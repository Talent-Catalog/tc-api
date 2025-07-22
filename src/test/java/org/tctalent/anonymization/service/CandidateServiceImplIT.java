package org.tctalent.anonymization.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.tctalent.anonymization.domain.document.CandidateDocument;
import org.tctalent.anonymization.mapper.DocumentMapper;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.CandidatePage;
import org.tctalent.anonymization.repository.CandidateDocumentRepository;

@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
class CandidateServiceImplIT {

  @Mock private CandidateDocumentRepository candidateDocumentRepository;
  @Mock private DocumentMapper documentMapper;
  @Mock private MongoTemplate mongoTemplate;

  @InjectMocks private CandidateServiceImpl candidateService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Test find all candidates")
  void testFindAll() {
    Pageable pageable = PageRequest.of(0, 10);
    CandidateDocument candidateDocument = new CandidateDocument();
    List<CandidateDocument> candidateDocuments = List.of(candidateDocument);
    Candidate candidate = new Candidate();
    CandidatePage expectedCandidatePage = new CandidatePage();

    when(mongoTemplate
        .find(any(Query.class), eq(CandidateDocument.class)))
        .thenReturn(candidateDocuments);

    when(mongoTemplate
        .count(any(Query.class), eq(CandidateDocument.class)))
        .thenReturn((long) candidateDocuments.size());

    when(documentMapper.toCandidateModel(any(CandidateDocument.class)))
        .thenReturn(candidate);

    when(documentMapper.toCandidateModelPage(any(Page.class)))
        .thenReturn(expectedCandidatePage);

    // includeEmployed set to false: should add a criteria excluding employed candidates.
    CandidatePage result = candidateService.findAll(pageable, null, null, null, null);

    // Capture and verify the query passed to mongoTemplate.find
    ArgumentCaptor<Query> findQueryCaptor = ArgumentCaptor.forClass(Query.class);
    verify(mongoTemplate).find(findQueryCaptor.capture(), eq(CandidateDocument.class));

    // Verify that the find query includes a filter: status != "employed"
    Query usedFindQuery = findQueryCaptor.getValue();
    Document findQueryObject = usedFindQuery.getQueryObject();
    assertTrue(findQueryObject.containsKey("status"), "Expected query to contain a 'status' filter");
    Document statusCriteria = (Document) findQueryObject.get("status");
    assertEquals("employed", statusCriteria.get("$ne"), "Expected $ne criteria to be 'employed'");

    // Capture and verify the query passed to mongoTemplate.count
    ArgumentCaptor<Query> countQueryCaptor = ArgumentCaptor.forClass(Query.class);
    verify(mongoTemplate).count(countQueryCaptor.capture(), eq(CandidateDocument.class));

    // Verify that the count query has the same filter
    Query usedCountQuery = countQueryCaptor.getValue();
    Document countQueryObject = usedCountQuery.getQueryObject();
    assertTrue(countQueryObject.containsKey("status"), "Expected count query to contain a 'status' filter");
    Document countStatusCriteria = (Document) countQueryObject.get("status");
    assertEquals("employed", countStatusCriteria.get("$ne"), "Expected count query $ne criteria to be 'employed'");

    assertNotNull(result);
    assertEquals(expectedCandidatePage, result);
  }

  @Test
  @DisplayName("Test find all candidates excluding employed candidates")
  void testFindAll_ExcludesEmployed() {
    Pageable pageable = PageRequest.of(0, 10);
    CandidateDocument candidateDocument = new CandidateDocument();
    List<CandidateDocument> candidateDocuments = List.of(candidateDocument);
    Candidate candidate = new Candidate();
    CandidatePage expectedCandidatePage = new CandidatePage();

    when(mongoTemplate
        .find(any(Query.class), eq(CandidateDocument.class)))
        .thenReturn(candidateDocuments);

    when(mongoTemplate
        .count(any(Query.class), eq(CandidateDocument.class)))
        .thenReturn((long) candidateDocuments.size());

    when(documentMapper.toCandidateModel(any(CandidateDocument.class)))
        .thenReturn(candidate);

    when(documentMapper.toCandidateModelPage(any(Page.class)))
        .thenReturn(expectedCandidatePage);

    // includeEmployed set to false: should add a criteria excluding employed candidates.
    CandidatePage result = candidateService.findAll(pageable, null, null, null, false);

    // Capture and verify the query passed to mongoTemplate.find
    ArgumentCaptor<Query> findQueryCaptor = ArgumentCaptor.forClass(Query.class);
    verify(mongoTemplate).find(findQueryCaptor.capture(), eq(CandidateDocument.class));

    // Verify that the find query includes a filter: status != "employed"
    Query usedFindQuery = findQueryCaptor.getValue();
    Document findQueryObject = usedFindQuery.getQueryObject();
    assertTrue(findQueryObject.containsKey("status"), "Expected query to contain a 'status' filter");
    Document statusCriteria = (Document) findQueryObject.get("status");
    assertEquals("employed", statusCriteria.get("$ne"), "Expected $ne criteria to be 'employed'");

    // Capture and verify the query passed to mongoTemplate.count
    ArgumentCaptor<Query> countQueryCaptor = ArgumentCaptor.forClass(Query.class);
    verify(mongoTemplate).count(countQueryCaptor.capture(), eq(CandidateDocument.class));

    // Verify that the count query has the same filter
    Query usedCountQuery = countQueryCaptor.getValue();
    Document countQueryObject = usedCountQuery.getQueryObject();
    assertTrue(countQueryObject.containsKey("status"), "Expected count query to contain a 'status' filter");
    Document countStatusCriteria = (Document) countQueryObject.get("status");
    assertEquals("employed", countStatusCriteria.get("$ne"), "Expected count query $ne criteria to be 'employed'");

    assertNotNull(result);
    assertEquals(expectedCandidatePage, result);
  }

  @Test
  @DisplayName("Test find all candidates including employed candidates")
  void testFindAll_IncludesEmployed() {
    Pageable pageable = PageRequest.of(0, 10);
    CandidateDocument candidateDocument = new CandidateDocument();
    List<CandidateDocument> candidateDocuments = List.of(candidateDocument);
    Candidate candidate = new Candidate();
    CandidatePage expectedCandidatePage = new CandidatePage();

    when(mongoTemplate
        .find(any(Query.class), eq(CandidateDocument.class)))
        .thenReturn(candidateDocuments);

    when(mongoTemplate
        .count(any(Query.class), eq(CandidateDocument.class)))
        .thenReturn((long) candidateDocuments.size());

    when(documentMapper
        .toCandidateModel(any(CandidateDocument.class)))
        .thenReturn(candidate);

    when(documentMapper
        .toCandidateModelPage(any(Page.class)))
        .thenReturn(expectedCandidatePage);

    // includeEmployed set to true: should not add a criteria to exclude employed candidates.
    CandidatePage result = candidateService.findAll(pageable, null, null, null, true);

    // Capture and verify the query passed to mongoTemplate.find
    ArgumentCaptor<Query> findQueryCaptor = ArgumentCaptor.forClass(Query.class);
    verify(mongoTemplate).find(findQueryCaptor.capture(), eq(CandidateDocument.class));

    // Verify that the find query does not include status filter
    Query usedFindQuery = findQueryCaptor.getValue();
    Document findQueryObject = usedFindQuery.getQueryObject();
    assertFalse(findQueryObject.containsKey("status"), "Expected query to not contain a 'status' filter when including employed");

    // Capture and verify the query passed to mongoTemplate.count
    ArgumentCaptor<Query> countQueryCaptor = ArgumentCaptor.forClass(Query.class);
    verify(mongoTemplate).count(countQueryCaptor.capture(), eq(CandidateDocument.class));

    // Verify that the count query does not include status filter
    Query usedCountQuery = countQueryCaptor.getValue();
    Document countQueryObject = usedCountQuery.getQueryObject();
    assertFalse(countQueryObject.containsKey("status"), "Expected count query to not contain a 'status' filter when including employed");

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

    assertEquals("Missing Candidate matching " + publicId, exception.getMessage());
  }
}
