package org.tctalent.anonymization.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.tctalent.anonymization.domain.document.CandidateDocument;
import org.tctalent.anonymization.domain.entity.CandidateEntity;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.IdentifiableCandidate;

/**
 * Performs anonymization
 *
 * @author John Cameron
 */
public interface AnonymizationService {

    /**
     * Converts full candidate into an anonymized version of it.
     * @param identifiableCandidate Full candidate data
     * @return Copy of data removing personal information
     * @throws JsonProcessingException if the data couldn't be converted
     */
    Candidate anonymizeToDto(IdentifiableCandidate identifiableCandidate) throws JsonProcessingException;

    /**
     * Converts full candidate represented as JSON String into an anonymized version of it.
     * @param json Full candidate data
     * @return Copy of data removing personal information
     * @throws JsonProcessingException if the data couldn't be converted
     */
    Candidate anonymizeToDto(String json) throws JsonProcessingException;

    /**
     * Converts full candidate into an anonymized Document.
     * @param identifiableCandidate Full candidate data
     * @return Copy of data removing personal information
     */
    CandidateDocument anonymizeToDocument(IdentifiableCandidate identifiableCandidate);

    /**
     * Converts full candidate into an anonymized JPA Entity.
     * @param identifiableCandidate Full candidate data
     * @return Copy of data removing personal information
     */
    CandidateEntity anonymizeToEntity(IdentifiableCandidate identifiableCandidate);
}
