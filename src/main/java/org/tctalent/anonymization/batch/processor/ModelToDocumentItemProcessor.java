package org.tctalent.anonymization.batch.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.domain.document.CandidateDocument;
import org.tctalent.anonymization.mapper.CandidateMapper;
import org.tctalent.anonymization.model.IdentifiableCandidate;


/**
 * Processor that implements the {@link ItemProcessor} interface to map
 * {@link IdentifiableCandidate} models into {@link CandidateDocument} objects. The mapping is
 * delegated to the {@link CandidateMapper}.
 * </p>
 * Used as part of the candidate migration batch process.
 *
 * @author sadatmalik
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ModelToDocumentItemProcessor implements
    ItemProcessor<IdentifiableCandidate, CandidateDocument> {

  private final CandidateMapper candidateMapper;

  @Override
  public CandidateDocument process(@NonNull final IdentifiableCandidate model) {
    return candidateMapper.anonymize(model);
  }

}
