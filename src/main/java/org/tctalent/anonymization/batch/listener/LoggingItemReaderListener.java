package org.tctalent.anonymization.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.logging.LogBuilder;
import org.tctalent.anonymization.model.IdentifiableCandidate;

/**
 * Listener that implements {@link ItemReadListener} to provide logging for errors that
 * occur while reading individual {@link IdentifiableCandidate} items.
 *
 * @author sadatmalik
 */
@Slf4j
@Component
public class LoggingItemReaderListener implements ItemReadListener<IdentifiableCandidate> {

  @Override
  public void onReadError(Exception exception) {
    LogBuilder.builder(log)
        .action("Error reading item from TC service")
        .message("Error message: " + exception.getMessage())
        .logError(exception);
  }

}
