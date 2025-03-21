package org.tctalent.anonymization.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.tctalent.anonymization.api.V1Api;
import org.tctalent.anonymization.domain.entity.ApiUser;
import org.tctalent.anonymization.dto.request.OfferToAssistRequest;
import org.tctalent.anonymization.dto.request.RegisterCandidateByPartnerRequest;
import org.tctalent.anonymization.exception.UnauthorisedActionException;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.CandidateAssistanceType;
import org.tctalent.anonymization.model.CandidateCoupon;
import org.tctalent.anonymization.model.CandidatePage;
import org.tctalent.anonymization.model.JobMatch201Response;
import org.tctalent.anonymization.model.JobMatchRequest;
import org.tctalent.anonymization.model.JobMatchRequestCandidatesInner;
import org.tctalent.anonymization.model.OfferToAssistCandidates201Response;
import org.tctalent.anonymization.model.OfferToAssistCandidatesRequest;
import org.tctalent.anonymization.model.RegisterCandidate201Response;
import org.tctalent.anonymization.model.RegisterCandidateRequest;
import org.tctalent.anonymization.security.AuthenticationService;
import org.tctalent.anonymization.service.CandidateService;
import org.tctalent.anonymization.service.TalentCatalogService;

@RestController
@RequiredArgsConstructor
public class CandidateController implements V1Api {

  public static final String BASE_URL = "/v1/candidates";

  private final AuthenticationService authenticationService;
  private final CandidateService candidateService;
  private final TalentCatalogService talentCatalogService;

  /**
   * {@inheritDoc}
   */
  @Override
  @PreAuthorize("hasAuthority('READ_CANDIDATE_DATA')")
  public ResponseEntity<CandidatePage> findCandidates(Integer page, Integer limit,
      String location, String nationality, String occupation) {
    Pageable pageable = PageRequest.of(page, limit);

    //Extract values from comma separated strings of filtering locations, nationalities and
    //occupations
    CandidatePage candidatesPage = candidateService.findAll(pageable,
        split(location), split(nationality), split(occupation));
    return ResponseEntity.ok(candidatesPage);
  }

  /**
   * Splits the given CSV string into a List of values
   * @param csv String containing comma separated values
   * @return null if csv is null
   */
  private List<String> split(@Nullable String csv) {
    List<String> values;
    if (csv == null) {
      values = null;
    } else {
      values = Arrays.stream(csv.split(","))
          .map(String::trim)
          .toList();
    }
    return values;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  @PreAuthorize("hasAuthority('READ_CANDIDATE_DATA')")
  public ResponseEntity<Candidate> getCandidateByPublicId(String publicId) {
    Candidate candidate = candidateService.findByPublicId(publicId);
    return ResponseEntity.ok(candidate);
  }

  @Override
  @PreAuthorize("hasAuthority('OFFER_CANDIDATE_SERVICES')")
  public ResponseEntity<OfferToAssistCandidates201Response> offerToAssistCandidates(
      OfferToAssistCandidatesRequest offerToAssistCandidatesRequest) {

    final OfferToAssistCandidates201Response response =
        doOfferToAssistCandidates(offerToAssistCandidatesRequest);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Override
  @PreAuthorize("hasAuthority('SUBMIT_JOB_MATCHES')")
  public ResponseEntity<JobMatch201Response> jobMatch(JobMatchRequest jobMatchRequest) {

    //JobMatch is just a special kind of OTA.
    //Convert JobMatchRequest to an equivalent OTA request
    OfferToAssistCandidatesRequest otaRequest = new OfferToAssistCandidatesRequest();
    otaRequest.setReason(CandidateAssistanceType.JOB_OPPORTUNITY);
    otaRequest.setAdditionalNotes(jobMatchRequest.getAdditionalNotes());

    //Job matches just have array of candidate ids - no coupons
    final List<JobMatchRequestCandidatesInner> candidates = jobMatchRequest.getCandidates();

    //Create array of CandidateCoupons one for each candidate plus a null coupon.
    final List<CandidateCoupon> candidateCoupons = candidates.stream()
        .map(c -> new CandidateCoupon(c.getPublicId()))
        .toList();
    otaRequest.setCandidates(candidateCoupons);

    final OfferToAssistCandidates201Response otaResponse = doOfferToAssistCandidates(otaRequest);

    //Convert OTA response into a JobMatch response
    JobMatch201Response response = new JobMatch201Response();
    response.setJobMatchId(otaResponse.getOfferId());

    //Job match has slightly different response message.
    response.setMessage("Your interest has been successfully recorded.");

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  /**
   * Send an OfferToAssistCandidates request to the server.
   * <p/>
   * Common code used by both {@link #offerToAssistCandidates} and {@link #jobMatch}.
   * @param offerToAssistCandidatesRequest Request to send
   * @return Response from server
   */
  private OfferToAssistCandidates201Response doOfferToAssistCandidates(
      OfferToAssistCandidatesRequest offerToAssistCandidatesRequest) {

    //Get the partner id of the public api user
    final Optional<ApiUser> currentApiUser = authenticationService.getCurrentApiUser();
    if (currentApiUser.isEmpty()) {
      throw new UnauthorisedActionException("offerToAssistCandidates");
    }
    long partnerId = currentApiUser.get().getPartner().getPartnerId();

    //Create an internal TC server request from the external request received from the public api user
    OfferToAssistRequest offerToAssistRequest = new OfferToAssistRequest(
        offerToAssistCandidatesRequest);
    //... and add the partner id of that user - retrieved above from the authentication service.
    offerToAssistRequest.setPartnerId(partnerId);

    //Log in if needed
    if (!talentCatalogService.isLoggedIn()) {
      talentCatalogService.login();
    }
    //...and send the request to the server
    return talentCatalogService.create(offerToAssistRequest);
  }

  @Override
  @PreAuthorize("hasAuthority('REGISTER_CANDIDATES')")
  public ResponseEntity<RegisterCandidate201Response> registerCandidate(
      RegisterCandidateRequest registerCandidateRequest) {

    //Get the partner id of the public api user
    final Optional<ApiUser> currentApiUser = authenticationService.getCurrentApiUser();
    if (currentApiUser.isEmpty()) {
      throw new UnauthorisedActionException("registerCandidate");
    }
    long partnerId = currentApiUser.get().getPartner().getPartnerId();

    //Create an internal TC server request from the external request received from the public api user
    RegisterCandidateByPartnerRequest registrationRequest = new RegisterCandidateByPartnerRequest(
        registerCandidateRequest);
    //... and add the partner id of that user - retrieved above from the authentication service.
    registrationRequest.setPartnerId(partnerId);

    //Log in if needed
    if (!talentCatalogService.isLoggedIn()) {
      talentCatalogService.login();
    }
    //...and send the request to the server
    final RegisterCandidate201Response response =
        talentCatalogService.register(registrationRequest);
    //...and return the response
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
