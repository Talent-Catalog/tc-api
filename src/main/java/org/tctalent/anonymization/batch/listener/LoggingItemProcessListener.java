package org.tctalent.anonymization.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.entity.db.Candidate;
import org.tctalent.anonymization.entity.mongo.CandidateDocument;
import org.tctalent.anonymization.logging.LogBuilder;

/**
 * Listener that implements {@link ItemProcessListener} to provide logging for errors that occur
 * while processing individual items.
 *
 * @author sadatmalik
 */
@Slf4j
@Component
public class LoggingItemProcessListener implements ItemProcessListener<Candidate, CandidateDocument> {

  @Override
  public void onProcessError(Candidate item, Exception e) {
    LogBuilder.builder(log)
        .action("Error processing item")
        .message("Item details:" + item.toString())
        .logError(e);
  }
}
