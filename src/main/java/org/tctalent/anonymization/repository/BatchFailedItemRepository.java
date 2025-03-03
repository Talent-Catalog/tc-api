package org.tctalent.anonymization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tctalent.anonymization.domain.entity.BatchFailedItem;

public interface BatchFailedItemRepository extends JpaRepository<BatchFailedItem, Long> {
}
