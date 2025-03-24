package org.tctalent.anonymization.batch.writer;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.domain.document.CandidateDocument;


/**
 * A Spring Batch {@link ItemWriter} implementation that writes {@link CandidateDocument} objects to
 * MongoDB using a find-and-replace (upsert) strategy.
 * <p>
 * For each {@code CandidateDocument} in the provided chunk, a query is built based on its
 * {@code publicId}. The writer then uses
 * {@link MongoTemplate#findAndReplace(Query, Object, FindAndReplaceOptions)} with upsert options to
 * either replace an existing document (if one with the same {@code publicId} exists) or insert a
 * new document (if none is found).
 * <p>
 * This means that the target MongoDB collection accurately reflects the unique set of source
 * documents by their {@code publicId}, preventing duplicates that might occur when chunk failures
 * and retries lead to additional writes.
 * <p>
 * Note: This writer assumes that the {@link CandidateDocument} collection includes a unique index
 * on {@code publicId}.
 *
 * @see MongoTemplate
 * @see FindAndReplaceOptions
 * @see CandidateDocument
 * @author sadatmalik
 */
@Component
@RequiredArgsConstructor
public class DocumentFindAndReplaceItemWriter implements ItemWriter<CandidateDocument> {

  private final MongoTemplate mongoTemplate;

  @Override
  public void write(Chunk<? extends CandidateDocument> chunk) throws Exception {
    // Create findAndReplace options to upsert and return the new document if needed.
    FindAndReplaceOptions options = FindAndReplaceOptions.options()
        .upsert()
        .returnNew();

    for (CandidateDocument doc : chunk) {
      // Build a query that matches a document by its publicId
      Query query = new Query(Criteria.where("publicId").is(doc.getPublicId()));

      // If a document with the given publicId exists, it will be replaced by the new doc.
      // Otherwise, a new document will be inserted.
      mongoTemplate.findAndReplace(query, doc, options);
    }
  }
}
