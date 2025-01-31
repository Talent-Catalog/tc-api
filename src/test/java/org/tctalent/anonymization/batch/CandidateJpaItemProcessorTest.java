package org.tctalent.anonymization.batch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tctalent.anonymization.batch.processor.CandidateJpaItemProcessor;
import org.tctalent.anonymization.entity.db.Candidate;
import org.tctalent.anonymization.entity.mongo.CandidateDocument;
import org.tctalent.anonymization.mapper.CandidateMapper;

/**
 * Unit tests for the {@link CandidateJpaItemProcessor} class.
 *
 * @author sadatmalik
 */
class CandidateJpaItemProcessorTest {

  @Mock
  private CandidateMapper candidateMapper;

  @InjectMocks
  private CandidateJpaItemProcessor candidateJpaItemProcessor;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Test jpa item processes")
  void testProcess() {
    Candidate candidate = new Candidate();
    CandidateDocument candidateDocument = new CandidateDocument();

    when(candidateMapper
        .anonymize(candidate))
        .thenReturn(candidateDocument);

    CandidateDocument result = candidateJpaItemProcessor.process(candidate);

    assertEquals(candidateDocument, result);
    verify(candidateMapper).anonymize(candidate);
  }
}
