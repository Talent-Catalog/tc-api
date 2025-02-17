package org.tctalent.anonymization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tctalent.anonymization.domain.entity.BatchFailedItemEntity;

public interface BatchFailedItemEntityRepository extends JpaRepository<BatchFailedItemEntity, Long> {
}
