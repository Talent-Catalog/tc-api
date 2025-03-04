package org.tctalent.anonymization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tctalent.anonymization.domain.entity.BatchFailedItem;
import org.tctalent.anonymization.repository.BatchFailedItemRepository;

@Service
@RequiredArgsConstructor
public class BatchFailedItemService {

  private final BatchFailedItemRepository batchFailedItemRepository;

  public void recordFailure(BatchFailedItem failedItem) {
    batchFailedItemRepository.save(failedItem);
  }
}
