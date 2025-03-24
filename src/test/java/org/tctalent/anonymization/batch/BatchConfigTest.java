package org.tctalent.anonymization.batch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.tctalent.anonymization.batch.config.BatchConfig;
import org.tctalent.anonymization.batch.config.BatchProperties;
import org.tctalent.anonymization.batch.listener.LoggingDocumentWriteListener;
import org.tctalent.anonymization.batch.listener.LoggingEntityWriteListener;
import org.tctalent.anonymization.batch.listener.LoggingJobExecutionListener;
import org.tctalent.anonymization.batch.listener.LoggingChunkListener;
import org.tctalent.anonymization.batch.listener.LoggingRestToDocumentProcessListener;
import org.tctalent.anonymization.batch.listener.LoggingRestToEntityProcessListener;
import org.tctalent.anonymization.batch.listener.LoggingRestReadListener;
import org.tctalent.anonymization.batch.listener.LoggingEntitySkipListener;
import org.tctalent.anonymization.batch.listener.LoggingDocumentSkipListener;
import org.tctalent.anonymization.batch.reader.RestApiItemReader;
import org.tctalent.anonymization.domain.entity.CandidateEntity;
import org.tctalent.anonymization.domain.document.CandidateDocument;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.repository.CandidateEntityRepository;
import org.tctalent.anonymization.repository.CandidateDocumentRepository;

/**
 * Unit tests for the {@link BatchConfig} class.
 *
 * @author sadatmalik
 */
class BatchConfigTest {

  @Mock private BatchProperties batchProperties;
  @Mock private JobRepository jobRepository;
  @Mock private DataSourceTransactionManager transactionManager;
  @Mock private ItemProcessor<IdentifiableCandidate, CandidateDocument> mongoItemProcessor;
  @Mock private ItemProcessor<IdentifiableCandidate, CandidateEntity> auroraItemProcessor;
  @Mock private ItemWriter<CandidateDocument> mongoItemWriter;
  @Mock private ItemWriter<CandidateEntity> auroraItemWriter;
  @Mock private LoggingChunkListener loggingChunkListener;
  @Mock private LoggingRestReadListener loggingRestReadListener;
  @Mock private LoggingRestToEntityProcessListener loggingRestToEntityProcessListener;
  @Mock private LoggingRestToDocumentProcessListener loggingRestToDocumentProcessListener;
  @Mock private LoggingEntityWriteListener loggingEntityWriteListener;
  @Mock private LoggingDocumentWriteListener loggingDocumentWriteListener;
  @Mock private LoggingDocumentSkipListener loggingDocumentSkipListener;
  @Mock private LoggingEntitySkipListener loggingEntitySkipListener;
  @Mock private CandidateEntityRepository candidateEntityRepository;
  @Mock private MongoTemplate mongoTemplate;

  @InjectMocks private BatchConfig batchConfig;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Test candidate migration job configuration")
  void testCandidateMigrationJob() {
    Step auroraMigrationStep = mock(Step.class);
    Step mongoMigrationStep = mock(Step.class);
    LoggingJobExecutionListener listener = mock(LoggingJobExecutionListener.class);

    Job job = batchConfig.candidateMigrationJob(jobRepository, auroraMigrationStep,
        mongoMigrationStep, listener);

    assertNotNull(job);
    assertEquals("candidateMigrationJob", job.getName());
  }

  @Test
  @DisplayName("Test candidate REST to aurora migration step configuration")
  void testCandidateRestToAuroraMigrationStep() {
    when(batchProperties
        .getChunkSize())
        .thenReturn(100);

    ItemReader<IdentifiableCandidate> tcItemReader = mock(RestApiItemReader.class);

    Step step = batchConfig.candidateRestToAuroraStep(
        jobRepository,
        transactionManager,
        tcItemReader,
        auroraItemProcessor,
        auroraItemWriter,
        loggingChunkListener,
        loggingRestReadListener,
        loggingRestToEntityProcessListener,
        loggingEntityWriteListener,
        loggingEntitySkipListener
    );

    assertNotNull(step);
    assertEquals("candidateRestToAuroraStep", step.getName());
  }

  @Test
  @DisplayName("Test candidate REST to mongo migration step configuration")
  void testCandidateRestToMongoMigrationStep() {
    when(batchProperties
        .getChunkSize())
        .thenReturn(100);

    ItemReader<IdentifiableCandidate> tcItemReader = mock(RestApiItemReader.class);

    Step step = batchConfig.candidateRestToMongoStep(
        jobRepository,
        tcItemReader,
        mongoItemProcessor,
        mongoItemWriter,
        loggingChunkListener,
        loggingRestReadListener,
        loggingRestToDocumentProcessListener,
        loggingDocumentWriteListener,
        loggingDocumentSkipListener
    );

    assertNotNull(step);
    assertEquals("candidateRestToMongoStep", step.getName());
  }

  @Test
  @DisplayName("Test Mongo item writer configuration")
  void testMongoItemWriter() {
    ItemWriter<CandidateDocument> writer = batchConfig.mongoItemWriter(mongoTemplate);

    assertNotNull(writer, "ItemWriter should not be null");
  }

  @Test
  @DisplayName("Test Aurora item writer configuration")
  void testAuroraItemWriter() {
    ItemWriter<CandidateEntity> writer = batchConfig.jpaItemWriter(candidateEntityRepository);

    assertNotNull(writer, "ItemWriter should not be null");
  }

}
