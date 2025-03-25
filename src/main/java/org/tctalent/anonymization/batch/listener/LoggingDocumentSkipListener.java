package org.tctalent.anonymization.batch.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.domain.document.CandidateDocument;
import org.tctalent.anonymization.logging.LogBuilder;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.service.BatchFailedItemService;
import org.tctalent.anonymization.domain.entity.BatchFailedItem;

@Slf4j
@Component
@RequiredArgsConstructor
@StepScope
public class LoggingDocumentSkipListener implements SkipListener<IdentifiableCandidate, CandidateDocument> {

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
    String message = String.format("Skipping item in PROCESSING for MongoDB due to error: %s, item: %s",
        t.getMessage(), publicId);

    LogBuilder.builder(log)
        .action("Skip processing")
        .message(message)
        .logWarn();

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
  public void onSkipInWrite(CandidateDocument item, Throwable t) {
    String publicId = String.valueOf(item.getId());
    String message = String.format("Skipping item in WRITING for MongoDB due to error: %s, item: %s",
        t.getMessage(), publicId);

    LogBuilder.builder(log)
        .action("Skip writing")
        .message(message)
        .logWarn();

    BatchFailedItem failedItem = BatchFailedItem.builder()
        .jobExecutionId(jobExecutionId)
        .stepExecutionId(stepExecutionId)
        .stepName(stepName)
        .itemType("CandidateDocument")
        .itemPublicId(publicId)
        .errorPhase("WRITING")
        .errorMessage(t.getMessage())
        .build();

    batchFailedItemService.recordFailure(failedItem);
  }
}
