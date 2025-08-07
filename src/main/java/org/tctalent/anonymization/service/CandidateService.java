package org.tctalent.anonymization.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.tctalent.anonymization.exception.NoSuchObjectException;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.CandidatePage;

public interface CandidateService {

  /**
   * Returns the requested page of candidates filtered by the values in the given lists if they
   * are not null. If there are multiple filters they all must be satisfied (ie "and").
   * @param pageable Defines the page required - including page number and size
   * @param locations List of locations
   * @param nationalities list of nationalities
   * @param occupations list of occupations
   * @return A page of candidates
   */
  CandidatePage findAll(Pageable pageable,
      @Nullable List<String> locations,
      @Nullable List<String> nationalities,
      @Nullable List<String> occupations,
      @Nullable Boolean includeEmployed);

  /**
   * Returns candidate associated with given id
   * @param publicId Public id
   * @return Candidate matching id
   * @throws NoSuchObjectException if no candidate exists with that id
   */
  Candidate findByPublicId(String publicId) throws NoSuchObjectException;

  /**
   * Returns a page of candidates associated with the given public list id.
   * <p>
   * @param pageable Defines the page required - including page number and size
   * @return A page of candidates
   */
  CandidatePage findByPublicListId(String publicListId, Pageable pageable);

}
