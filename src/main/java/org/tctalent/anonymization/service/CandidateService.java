package org.tctalent.anonymization.service;

import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.CandidatePage;

public interface CandidateService {

  CandidatePage findAll(Pageable pageable);

  Candidate findByPublicId(String publicId);
}
