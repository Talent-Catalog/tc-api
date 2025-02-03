package org.tctalent.anonymization.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.domain.entity.CandidateEntity;
import org.tctalent.anonymization.logging.LogBuilder;
import org.tctalent.anonymization.model.Candidate;

/**
 * Listener that implements {@link ItemProcessListener} to provide logging for errors that occur
 * while processing individual rest to entity items.
 *
 * @author sadatmalik
 */
@Slf4j
@Component
public class LoggingRestToEntityProcessListener implements ItemProcessListener<Candidate, CandidateEntity> {

  @Override
  public void onProcessError(Candidate item, Exception e) {
    LogBuilder.builder(log)
        .action("Error processing item")
        .message("Item details:" + item.toString())
        .logError(e);
  }
}
