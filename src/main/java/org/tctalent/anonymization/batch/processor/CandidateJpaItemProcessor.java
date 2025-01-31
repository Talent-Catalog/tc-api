package org.tctalent.anonymization.batch.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.entity.db.Candidate;
import org.tctalent.anonymization.entity.mongo.CandidateDocument;
import org.tctalent.anonymization.mapper.CandidateMapper;


/**
 * Processor that implements the {@link ItemProcessor} interface to map {@link Candidate} entities
 * into {@link CandidateDocument} objects. The mapping is delegated to the {@link CandidateMapper}.
 * </p>
 * Used as part of the candidate migration batch process.
 *
 * @author sadatmalik
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class CandidateJpaItemProcessor implements ItemProcessor<Candidate, CandidateDocument> {

  private final CandidateMapper candidateMapper;

  @Override
  public CandidateDocument process(@NonNull final Candidate entity) {
    return candidateMapper.anonymize(entity);
  }

}
