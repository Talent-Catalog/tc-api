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
import org.tctalent.anonymization.exception.UnauthorisedActionException;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.CandidatePage;
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

    final Optional<ApiUser> currentApiUser = authenticationService.getCurrentApiUser();
    if (currentApiUser.isEmpty()) {
      throw new UnauthorisedActionException("offerToAssistCandidates");
    }
    long partnerId = currentApiUser.get().getPartner().getPartnerId();

    if (!talentCatalogService.isLoggedIn()) {
      talentCatalogService.login();
    }
    OfferToAssistRequest offerToAssistRequest = new OfferToAssistRequest(offerToAssistCandidatesRequest);
    offerToAssistRequest.setPartnerId(partnerId);
    return new ResponseEntity<>(talentCatalogService.create(offerToAssistRequest), HttpStatus.CREATED);
  }

  @Override
  @PreAuthorize("hasAuthority('REGISTER_CANDIDATES')")
  public ResponseEntity<RegisterCandidate201Response> registerCandidate(
      RegisterCandidateRequest registerCandidateRequest) {

    //TODO JC Connect to tctalent server and simulate a series of updates mimicking a normal registration
    //TODO JC The server should send the candidate an email notifying them of their registration with instructions on how to log in.
    //TODO JC Return the public id of the created candidate as well as the message and request id in the RegisterCandidate201Response

    return V1Api.super.registerCandidate(registerCandidateRequest);
  }
}
