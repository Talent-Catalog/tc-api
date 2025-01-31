package org.tctalent.anonymization.batch.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.domain.entity.AnonymousCandidate;
import org.tctalent.anonymization.mapper.EntityMapper;
import org.tctalent.anonymization.model.IdentifiableCandidate;

@Slf4j
@RequiredArgsConstructor
@Component
public class ModelToEntityItemProcessor implements
    ItemProcessor<IdentifiableCandidate, AnonymousCandidate> {

  private final EntityMapper candidateMapper;

  @Override
  public AnonymousCandidate process(@NonNull final IdentifiableCandidate model) {
    return candidateMapper.anonymize(model);
  }

}
