package org.tctalent.anonymization.batch.processor;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
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
  private final Validator validator;

  @Valid
  @Override
  public CandidateEntity process(@NonNull final IdentifiableCandidate model) {
    CandidateEntity entity = anonymizationService.anonymizeToEntity(model);

    // Perform validation - we do it this way so validation happens before hibernate attempts to
    // commit the processed entity. Validation failures during Hibernate commits cause the entire
    // batch chunk to fail, whereas this way a single item failure is skipped, logged, and
    // batch processing continues from the next item
    Set<ConstraintViolation<CandidateEntity>> violations = validator.validate(entity);
    if (!violations.isEmpty()) {
      String message = violations.stream()
          .map(v -> v.getPropertyPath() + ": " + v.getMessage())
          .collect(Collectors.joining("; "));

      // todo - sm - use log builder
      log.warn("Validation failed for CandidateEntity: {}", message);

      // Throw an exception so Spring Batch can skip the item and continue processing the next item
      throw new ValidationException("CandidateEntity validation failed: " + message);
    }

    return entity;
  }

}
