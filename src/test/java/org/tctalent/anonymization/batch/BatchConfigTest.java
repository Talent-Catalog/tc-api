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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.tctalent.anonymization.batch.config.BatchConfig;
import org.tctalent.anonymization.batch.config.BatchProperties;
import org.tctalent.anonymization.entity.db.Candidate;
import org.tctalent.anonymization.entity.mongo.CandidateDocument;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.repository.CandidateMongoRepository;
import org.tctalent.anonymization.repository.CandidateRepository;

/**
 * Unit tests for the {@link BatchConfig} class.
 *
 * @author sadatmalik
 */
class BatchConfigTest {

  @Mock private BatchProperties batchProperties;
  @Mock private JobRepository jobRepository;
  @Mock private DataSourceTransactionManager transactionManager;
  @Mock private ItemReader<Candidate> jpaItemReader;
  @Mock private ItemProcessor<Candidate, CandidateDocument> jpaItemProcessor;
  @Mock private ItemProcessor<IdentifiableCandidate, CandidateDocument> restItemProcessor;
  @Mock private ItemWriter<CandidateDocument> mongoItemWriter;
  @Mock private LoggingChunkListener loggingChunkListener;
  @Mock private LoggingItemReaderListener loggingItemReaderListener;
  @Mock private LoggingItemProcessListener loggingItemProcessListener;
  @Mock private LoggingItemWriterListener loggingItemWriterListener;
  @Mock private CandidateRepository candidateRepository;
  @Mock private CandidateMongoRepository candidateMongoRepository;

  @InjectMocks private BatchConfig batchConfig;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Test candidate migration job configuration")
  void testCandidateMigrationJob() {
    Step candidateMigrationStep = mock(Step.class);
    JobCompletionNotificationListener listener = mock(JobCompletionNotificationListener.class);

    Job job = batchConfig.candidateMigrationJob(jobRepository, candidateMigrationStep, listener);

    assertNotNull(job);
    assertEquals("candidateMigrationJob", job.getName());
  }

  @Test
  @DisplayName("Test candidate JPA migration step configuration")
  void testCandidateJpaMigrationStep() {
    when(batchProperties
        .getChunkSize())
        .thenReturn(100);

    Step step = batchConfig.candidateJpaMigrationStep(
        jobRepository,
        transactionManager,
        jpaItemReader,
        jpaItemProcessor,
        mongoItemWriter,
        loggingChunkListener,
        loggingItemReaderListener,
        loggingItemProcessListener,
        loggingItemWriterListener
    );

    assertNotNull(step);
    assertEquals("candidateJpaMigrationStep", step.getName());
  }

  @Test
  @DisplayName("Test candidate REST migration step configuration")
  void testCandidateRestMigrationStep() {
//    when(batchProperties
//        .getChunkSize())
//        .thenReturn(100);
//
//    ItemReader<IdentifiableCandidate> tcItemReader = mock(RestApiItemReader.class);
//
//    Step step = batchConfig.candidateRestMigrationStep(
//        jobRepository,
//        transactionManager,
//        tcItemReader,
//        restItemProcessor,
//        mongoItemWriter,
//        loggingChunkListener,
//        loggingItemReaderListener,
//        loggingItemProcessListener,
//        loggingItemWriterListener
//    );
//
//    assertNotNull(step);
//    assertEquals("candidateRestMigrationStep", step.getName());
  }

  @Test
  @DisplayName("Test JPA item reader configuration")
  void testJpaItemReader() {
    when(batchProperties
        .getPageSize())
        .thenReturn(100);

    ItemReader<Candidate> reader = batchConfig.jpaItemReader(candidateRepository);

    assertNotNull(reader);
  }

  @Test
  @DisplayName("Test Mongo item writer configuration")
  void testMongoItemWriter() {
    ItemWriter<CandidateDocument> writer = batchConfig.mongoItemWriter(candidateMongoRepository);

    assertNotNull(writer);
  }
}
