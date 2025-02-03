package org.tctalent.anonymization.mapper;

import org.mapstruct.Mapper;
import org.tctalent.anonymization.domain.entity.CandidateEntity;
import org.tctalent.anonymization.model.IdentifiableCandidate;

@Mapper
public interface EntityMapper {
  CandidateEntity anonymize(IdentifiableCandidate candidate);
}
