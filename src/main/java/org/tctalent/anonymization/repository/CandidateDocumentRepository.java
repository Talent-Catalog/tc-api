package org.tctalent.anonymization.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tctalent.anonymization.domain.document.CandidateDocument;

@Repository
public interface CandidateDocumentRepository extends MongoRepository<CandidateDocument, String> {
  Optional<CandidateDocument> findByPublicId(String publicId);
}
