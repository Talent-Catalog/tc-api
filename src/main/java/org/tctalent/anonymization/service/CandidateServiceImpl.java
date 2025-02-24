package org.tctalent.anonymization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.tctalent.anonymization.exception.NoSuchObjectException;
import org.tctalent.anonymization.mapper.DocumentMapper;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.CandidatePage;
import org.tctalent.anonymization.repository.CandidateDocumentRepository;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

  private final CandidateDocumentRepository candidateDocumentRepository;
  private final DocumentMapper documentMapper;

  @Override
  public CandidatePage findAll(Pageable pageable) {
    Page<Candidate> candidatePage =  candidateDocumentRepository
        .findAll(pageable)
        .map(documentMapper::toCandidateModel);

    return documentMapper.toCandidateModelPage(candidatePage);
  }

  @Override
  public Candidate findByPublicId(String publicId) {
    return candidateDocumentRepository
        .findByPublicId(publicId)
        .map(documentMapper::toCandidateModel)
        .orElseThrow(() -> new NoSuchObjectException(Candidate.class, publicId));
  }

}
