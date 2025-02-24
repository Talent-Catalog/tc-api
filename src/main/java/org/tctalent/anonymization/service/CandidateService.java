package org.tctalent.anonymization.service;

import org.springframework.data.domain.Pageable;
import org.tctalent.anonymization.exception.NoSuchObjectException;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.CandidatePage;

public interface CandidateService {

  /**
   * Returns the requested page of candidates
   * @param pageable Defines the page required - including page number and size
   * @return A page of candidates
   */
  CandidatePage findAll(Pageable pageable);

  /**
   * Returns candidate associated with given id
   * @param publicId Public id
   * @return Candidate matching id
   * @throws NoSuchObjectException if no candidate exists with that id
   */
  Candidate findByPublicId(String publicId) throws NoSuchObjectException;
}
