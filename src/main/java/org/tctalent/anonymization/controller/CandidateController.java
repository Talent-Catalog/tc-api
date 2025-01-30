package org.tctalent.anonymization.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.tctalent.anonymization.api.V1Api;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.CandidatePage;
import org.tctalent.anonymization.service.CandidateService;

@RestController
@RequiredArgsConstructor
public class CandidateController implements V1Api {

  public static final String BASE_URL = "/v1/candidates";

  private final CandidateService candidateService;

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<CandidatePage> getAllCandidates(Integer page, Integer limit,
      String occupation, String location, String nationality) {
    // Adjust page index because Spring Data JPA uses zero-based paging
    // todo sm might be better to just change the API to require page 0 for the first page
    // todo sm set a max limit - 50?
    // todo sm handle occupation, location, nationality
    Pageable pageable = PageRequest.of(page - 1, limit);
    CandidatePage candidatesPage = candidateService.findAll(pageable);
    return ResponseEntity.ok(candidatesPage);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Candidate> getCandidateByPublicId(String publicId) {
    Candidate candidate = candidateService.findByPublicId(publicId);
    return ResponseEntity.ok(candidate);
  }

}
