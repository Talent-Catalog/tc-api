package org.tctalent.anonymization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import org.tctalent.anonymization.domain.entity.CandidateEntity;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {

}
