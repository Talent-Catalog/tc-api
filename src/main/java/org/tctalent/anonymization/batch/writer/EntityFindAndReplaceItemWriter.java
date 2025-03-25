package org.tctalent.anonymization.batch.writer;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.domain.entity.CandidateEntity;
import org.tctalent.anonymization.repository.CandidateEntityRepository;


/**
 * A Spring Batch {@link ItemWriter} implementation for {@link CandidateEntity} that performs a
 * "find and replace" operation against the Aurora database.
 * <p>
 * For each {@code CandidateEntity} in the input chunk, this writer:
 * <ol>
 *   <li>Checks if an entity with the same {@code publicId} exists by calling
 *       {@link CandidateEntityRepository#findByPublicId(String)}.</li>
 *   <li>If found, deletes the existing entity and immediately flushes the change to the database to avoid
 *       unique key conflicts.</li>
 *   <li>Saves and flushes the new entity, thereby replacing any previous record with the same {@code publicId}.</li>
 * </ol>
 * </p>
 * <p>
 * This writer is executed within the transactional boundaries provided by the Spring Batch step,
 * so there is no need for additional {@code @Transactional} annotations on the {@code write} method.
 *
 * @author sadatmalik
 */
@Component
@RequiredArgsConstructor
public class EntityFindAndReplaceItemWriter implements ItemWriter<CandidateEntity> {

  private final CandidateEntityRepository candidateEntityRepository;

  @Override
  public void write(Chunk<? extends CandidateEntity> chunk) throws Exception {
    for (CandidateEntity newEntity : chunk) {
      Optional<CandidateEntity> existingOpt = candidateEntityRepository.findByPublicId(newEntity.getPublicId());
      if (existingOpt.isPresent()) {
        // Delete the existing entity.
        candidateEntityRepository.delete(existingOpt.get());
        candidateEntityRepository.flush();
      }
      // Save and flush the new entity.
      candidateEntityRepository.saveAndFlush(newEntity);
    }
  }

}
