package org.tctalent.anonymization.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.tctalent.anonymization.domain.document.CandidateDocument;
import org.tctalent.anonymization.mapper.DocumentMapper;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.CandidatePage;
import org.tctalent.anonymization.repository.CandidateDocumentRepository;

class CandidateServiceImplTest {

  @Mock private CandidateDocumentRepository candidateDocumentRepository;
  @Mock private DocumentMapper documentMapper;

  @InjectMocks private CandidateServiceImpl candidateService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Test find all candidates")
  void testFindAll() {
    Pageable pageable = PageRequest.of(0, 10);
    Page<CandidateDocument> candidateDocumentPage = new PageImpl<>(List.of(new CandidateDocument()));
    Candidate candidate = new Candidate();
    Page<Candidate> candidatePage = new PageImpl<>(List.of(candidate));
    CandidatePage expectedCandidatePage = new CandidatePage();

    when(candidateDocumentRepository
        .findAll(pageable))
        .thenReturn(candidateDocumentPage);

    when(documentMapper
        .toCandidateModel(any(CandidateDocument.class)))
        .thenReturn(candidate);

    when(documentMapper
        .toCandidateModelPage(candidatePage))
        .thenReturn(expectedCandidatePage);

    CandidatePage result = candidateService.findAll(pageable, null, null, null);

    assertNotNull(result);
    assertEquals(expectedCandidatePage, result);
  }

  @Test
  @DisplayName("Test find candidate by id")
  void testFindById() {
    String publicId = "base-64-encoded-uuid";
    CandidateDocument candidateDocument = new CandidateDocument();
    Candidate candidate = new Candidate();

    when(candidateDocumentRepository
        .findFirstByPublicId(publicId))
        .thenReturn(Optional.of(candidateDocument));

    when(documentMapper
        .toCandidateModel(candidateDocument))
        .thenReturn(candidate);

    Candidate result = candidateService.findByPublicId(publicId);

    assertNotNull(result);
    assertEquals(candidate, result);
  }

  @Test
  @DisplayName("Test find candidate by id not found")
  void testFindById_NotFound() {
    String publicId = "base-64-encoded-uuid";

    when(candidateDocumentRepository
        .findFirstByPublicId(publicId))
        .thenReturn(Optional.empty());

    Exception exception = assertThrows(
        RuntimeException.class,
        () -> candidateService.findByPublicId(publicId),
        "Should throw an exception if the candidate is not found"
    );

    assertEquals("Missing Candidate matching base-64-encoded-uuid", exception.getMessage());
  }
}
