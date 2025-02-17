package org.tctalent.anonymization.batch.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.domain.entity.BatchFailedItemEntity;
import org.tctalent.anonymization.domain.entity.CandidateEntity;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.service.BatchFailedItemService;


@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingSkipListener implements SkipListener<IdentifiableCandidate, CandidateEntity>,
    StepExecutionListener {

  private final BatchFailedItemService batchFailedItemService;

  private Long jobExecutionId;
  private Long stepExecutionId;
  private String stepName;

  // Capture StepExecution info to use in onSkip callbacks
  @Override
  public void beforeStep(StepExecution stepExecution) {
    this.jobExecutionId = stepExecution.getJobExecution().getId();
    this.stepExecutionId = stepExecution.getId();
    this.stepName = stepExecution.getStepName();
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    return stepExecution.getExitStatus();
  }

  @Override
  public void onSkipInProcess(IdentifiableCandidate item, Throwable t) {
    String publicId = item.getPublicId();
    // todo log builder
    log.warn("Skipping item in PROCESSING due to error: {}, item: {}", t.getMessage(), publicId);

    BatchFailedItemEntity failedItem = BatchFailedItemEntity.builder()
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

    BatchFailedItemEntity failedItem = BatchFailedItemEntity.builder()
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
