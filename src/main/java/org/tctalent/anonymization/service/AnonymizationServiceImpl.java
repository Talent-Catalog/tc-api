package org.tctalent.anonymization.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tctalent.anonymization.domain.document.CandidateDocument;
import org.tctalent.anonymization.domain.entity.CandidateEntity;
import org.tctalent.anonymization.mapper.DocumentMapper;
import org.tctalent.anonymization.mapper.EntityMapper;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.IdentifiableCandidate;

/**
 * Copies fields from Candidate where there is a matching field in AnonCandidate
 * </p>
 * Maps from an IdentifiableCandidate to CandidateDocument.
 * </p>
 * Maps from an IdentifiableCandidate to CandidateEntity.
 *
 * @author John Cameron
 */
@Service
@RequiredArgsConstructor
public class AnonymizationServiceImpl implements AnonymizationService{
    private final ObjectMapper mapper;
    private final EntityMapper entityMapper;
    private final DocumentMapper documentMapper;

    @Override
    public Candidate anonymizeToDto(IdentifiableCandidate candidate) throws JsonProcessingException {
        String json = mapper.writeValueAsString(candidate);
        return anonymizeToDto(json);
    }

    @Override
    public Candidate anonymizeToDto(String json) throws JsonProcessingException {
        return mapper.readValue(json, Candidate.class);
    }

    @Override
    public CandidateDocument anonymizeToDocument(final IdentifiableCandidate identifiableCandidate) {
        return documentMapper.anonymize(identifiableCandidate);
    }

    @Override
    public CandidateEntity anonymizeToEntity(final IdentifiableCandidate identifiableCandidate) {
        return entityMapper.anonymize(identifiableCandidate);
    }
}
