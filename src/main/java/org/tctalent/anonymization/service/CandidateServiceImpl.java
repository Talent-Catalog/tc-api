package org.tctalent.anonymization.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.tctalent.anonymization.mapper.CandidateMapper;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.CandidatePage;
import org.tctalent.anonymization.repository.CandidateMongoRepository;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

  private final CandidateMongoRepository candidateMongoRepository;
  private final CandidateMapper candidateMapper;

  @Override
  public CandidatePage findAll(Pageable pageable) {
    Page<Candidate> candidatePage =  candidateMongoRepository
        .findAll(pageable)
        .map(candidateMapper::toCandidateModel);

    return candidateMapper.toCandidateModelPage(candidatePage);
  }

  @Override
  public Candidate findByPublicId(UUID publicId) {
    return candidateMongoRepository
        .findByPublicId(publicId)
        .map(candidateMapper::toCandidateModel)
        .orElseThrow(() -> new RuntimeException("Candidate not found")); // todo better exceptions
  }

}
