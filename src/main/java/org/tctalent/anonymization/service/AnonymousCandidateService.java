package org.tctalent.anonymization.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.Base64;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tctalent.anonymization.domain.entity.AnonymousCandidate;
import org.tctalent.anonymization.repository.CandidateAuroraRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnonymousCandidateService {

  private final CandidateAuroraRepository candidateAuroraRepository;
  private final EntityManager entityManager;

  @Transactional
  public void saveAnonymousCandidate() {
    log.info("Bootstrapping an anonymous candidate into Aurora DB...");

    // Create an AnonymousCandidate object
    AnonymousCandidate candidate = new AnonymousCandidate();
    candidate.setPublicId(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()));

    // Save to DB
    candidateAuroraRepository.save(candidate);
    entityManager.flush();  // Ensure data is written to DB immediately
    entityManager.clear();  // Clear Hibernate session cache

    log.info("Anonymous Candidate saved successfully with publicId: {}", candidate.getPublicId());

    // Verify that the candidate was persisted
    candidateAuroraRepository.findById(candidate.getId()).ifPresentOrElse(
        found -> log.info("Candidate found in DB: {}", found),
        () -> log.warn("Candidate NOT found in DB!")
    );
  }
}
