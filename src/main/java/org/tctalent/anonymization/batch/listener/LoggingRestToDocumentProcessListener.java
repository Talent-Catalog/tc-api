package org.tctalent.anonymization.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.domain.document.CandidateDocument;
import org.tctalent.anonymization.logging.LogBuilder;
import org.tctalent.anonymization.model.Candidate;

/**
 * Listener that implements {@link ItemProcessListener} to provide logging for errors that occur
 * while processing individual rest to document items.
 *
 * @author sadatmalik
 */
@Slf4j
@Component
public class LoggingRestToDocumentProcessListener implements ItemProcessListener<Candidate, CandidateDocument> {

  @Override
  public void onProcessError(Candidate item, Exception e) {
    LogBuilder.builder(log)
        .action("Error processing item")
        .message("Item details:" + item.toString())
        .logError(e);
  }
}
