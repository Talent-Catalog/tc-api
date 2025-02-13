package org.tctalent.anonymization.batch.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.domain.entity.CandidateEntity;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.service.AnonymizationService;


/**
 * Processor that implements the {@link ItemProcessor} interface and maps
 * {@link IdentifiableCandidate} models into anonymised {@link CandidateEntity} objects. The
 * mapping is delegated to the {@link AnonymizationService}.
 * </p>
 * Used as part of the candidate migration batch process.
 *
 * @author sadatmalik
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ModelToEntityItemProcessor implements
    ItemProcessor<IdentifiableCandidate, CandidateEntity> {

  private final AnonymizationService anonymizationService;

  @Override
  public CandidateEntity process(@NonNull final IdentifiableCandidate model) {
    return anonymizationService.anonymizeToEntity(model);
  }

}
