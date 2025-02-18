package org.tctalent.anonymization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tctalent.anonymization.domain.entity.BatchFailedItemEntity;
import org.tctalent.anonymization.repository.BatchFailedItemEntityRepository;

@Service
@RequiredArgsConstructor
public class BatchFailedItemService {

  private final BatchFailedItemEntityRepository batchFailedItemRepository;

  public void recordFailure(BatchFailedItemEntity failedItem) {
    batchFailedItemRepository.save(failedItem);
  }
}
