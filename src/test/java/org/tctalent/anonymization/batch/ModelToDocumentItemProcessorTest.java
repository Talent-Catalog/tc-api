package org.tctalent.anonymization.batch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tctalent.anonymization.batch.processor.ModelToDocumentItemProcessor;
import org.tctalent.anonymization.domain.document.CandidateDocument;
import org.tctalent.anonymization.mapper.DocumentMapper;
import org.tctalent.anonymization.model.IdentifiableCandidate;

/**
 * Unit tests for the {@link ModelToDocumentItemProcessor} class.
 *
 * @author sadatmalik
 */
class ModelToDocumentItemProcessorTest {

  @Mock
  private DocumentMapper documentMapper;

  @InjectMocks
  private ModelToDocumentItemProcessor candidateRestItemProcessor;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Test rest item processes")
  void testProcess() {
    IdentifiableCandidate identifiableCandidate = new IdentifiableCandidate();
    CandidateDocument candidateDocument = new CandidateDocument();

    when(documentMapper
        .anonymize(identifiableCandidate))
        .thenReturn(candidateDocument);

    CandidateDocument result = candidateRestItemProcessor.process(identifiableCandidate);

    assertEquals(candidateDocument, result);
    verify(documentMapper).anonymize(identifiableCandidate);
  }
}
