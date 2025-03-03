package org.tctalent.anonymization.batch.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.domain.entity.BatchFailedItem;
import org.tctalent.anonymization.domain.entity.CandidateEntity;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.service.BatchFailedItemService;


@Slf4j
@Component
@RequiredArgsConstructor
@StepScope
public class LoggingSkipListener implements SkipListener<IdentifiableCandidate, CandidateEntity> {

  private final BatchFailedItemService batchFailedItemService;

  @Value("#{stepExecution.jobExecution.id}")
  private Long jobExecutionId;

  @Value("#{stepExecution.id}")
  private Long stepExecutionId;

  @Value("#{stepExecution.stepName}")
  private String stepName;

  @Override
  public void onSkipInProcess(IdentifiableCandidate item, Throwable t) {
    String publicId = item.getPublicId();
    // todo log builder
    log.warn("Skipping item in PROCESSING due to error: {}, item: {}", t.getMessage(), publicId);

    BatchFailedItem failedItem = BatchFailedItem.builder()
        .jobExecutionId(jobExecutionId)
        .stepExecutionId(stepExecutionId)
        .stepName(stepName)
        .itemType("IdentifiableCandidate")
        .itemPublicId(publicId)
        .errorPhase("PROCESSING")
        .errorMessage(t.getMessage())
        .build();

    batchFailedItemService.recordFailure(failedItem);
  }

  @Override
  public void onSkipInWrite(CandidateEntity item, Throwable t) {
    String publicId = item.getPublicId();
    // todo log builder
    log.warn("Skipping item in WRITING due to error: {}, item: {}", t.getMessage(), publicId);

    BatchFailedItem failedItem = BatchFailedItem.builder()
        .jobExecutionId(jobExecutionId)
        .stepExecutionId(stepExecutionId)
        .stepName(stepName)
        .itemType("CandidateEntity")
        .itemPublicId(publicId)
        .errorPhase("WRITING")
        .errorMessage(t.getMessage())
        .build();

    batchFailedItemService.recordFailure(failedItem);
  }


}
