package org.tctalent.anonymization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.tctalent.anonymization.domain.entity.CandidateEntity;

@Repository
public interface CandidateAuroraRepository extends JpaRepository<CandidateEntity, Long> {
  Optional<CandidateEntity> findByPublicId(String publicId);
}
