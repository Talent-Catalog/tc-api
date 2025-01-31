package org.tctalent.anonymization.batch.listener;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.entity.mongo.CandidateDocument;
import org.tctalent.anonymization.logging.LogBuilder;

/**
 * Listener that implements {@link ItemWriteListener} to provide logging for errors that occur while
 * writing chunks of document items.
 *
 * @author sadatmalik
 */
@Slf4j
@Component
public class LoggingDocumentWriteListener implements ItemWriteListener<CandidateDocument> {

  @Override
  public void beforeWrite(Chunk<? extends CandidateDocument> items) {
    String itemDetails = items.getItems().stream()
        .map(Object::toString)
        .collect(Collectors.joining(", "));

    LogBuilder.builder(log)
        .action("Writing items")
        .message("Items to write:" + itemDetails)
        .logInfo();
  }

  @Override
  public void afterWrite(Chunk<? extends CandidateDocument> items) {
    String itemDetails = items.getItems().stream()
        .map(Object::toString)
        .collect(Collectors.joining(", "));

    LogBuilder.builder(log)
        .action("Items written successfully")
        .message("Items written:" + itemDetails)
        .logInfo();
  }

  @Override
  public void onWriteError(Exception exception, Chunk<? extends CandidateDocument> items) {
    String itemDetails = items.getItems().stream()
        .map(Object::toString)
        .collect(Collectors.joining(", "));

    LogBuilder.builder(log)
        .action("Error writing items")
        .message("Failed to write items:" + itemDetails)
        .logError(exception);
  }

}
