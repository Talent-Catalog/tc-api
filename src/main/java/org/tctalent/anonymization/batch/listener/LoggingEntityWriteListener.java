package org.tctalent.anonymization.batch.listener;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.domain.entity.AnonymousCandidate;
import org.tctalent.anonymization.logging.LogBuilder;

/**
 * Listener that implements {@link ItemWriteListener} to provide logging for errors that occur while
 * writing chunks of entity items.
 *
 * @author sadatmalik
 */
@Slf4j
@Component
public class LoggingEntityWriteListener implements ItemWriteListener<AnonymousCandidate> {

  @Override
  public void beforeWrite(Chunk<? extends AnonymousCandidate> items) {
    String itemDetails = items.getItems().stream()
        .map(Object::toString)
        .collect(Collectors.joining(", "));

    LogBuilder.builder(log)
        .action("Writing entity items")
        .message("Entity items to write:" + itemDetails)
        .logInfo();
  }

  @Override
  public void afterWrite(Chunk<? extends AnonymousCandidate> items) {
    String itemDetails = items.getItems().stream()
        .map(Object::toString)
        .collect(Collectors.joining(", "));

    LogBuilder.builder(log)
        .action("Entity items written successfully")
        .message("Entity items written:" + itemDetails)
        .logInfo();
  }

  @Override
  public void onWriteError(Exception exception, Chunk<? extends AnonymousCandidate> items) {
    String itemDetails = items.getItems().stream()
        .map(Object::toString)
        .collect(Collectors.joining(", "));

    LogBuilder.builder(log)
        .action("Error writing entity items")
        .message("Failed to write entity items:" + itemDetails)
        .logError(exception);
  }

}
