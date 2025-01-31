package org.tctalent.anonymization.mapper;

import org.mapstruct.Mapper;
import org.tctalent.anonymization.domain.entity.AnonymousCandidate;
import org.tctalent.anonymization.model.IdentifiableCandidate;

@Mapper
public interface EntityMapper {
  AnonymousCandidate anonymize(IdentifiableCandidate candidate);
}
