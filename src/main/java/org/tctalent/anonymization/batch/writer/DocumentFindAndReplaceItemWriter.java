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


// todo Javadoc
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
