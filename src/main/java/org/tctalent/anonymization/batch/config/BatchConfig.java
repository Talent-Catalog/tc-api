package org.tctalent.anonymization.batch.config;

import lombok.RequiredArgsConstructor;
import org.bson.codecs.configuration.CodecConfigurationException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.PlatformTransactionManager;
import org.tctalent.anonymization.batch.listener.LoggingDocumentWriteListener;
import org.tctalent.anonymization.batch.listener.LoggingJobExecutionListener;
import org.tctalent.anonymization.batch.listener.LoggingChunkListener;
import org.tctalent.anonymization.batch.listener.LoggingRestToDocumentProcessListener;
import org.tctalent.anonymization.batch.listener.LoggingRestToEntityProcessListener;
import org.tctalent.anonymization.batch.listener.LoggingRestReadListener;
import org.tctalent.anonymization.batch.listener.LoggingEntityWriteListener;
import org.tctalent.anonymization.batch.listener.LoggingEntitySkipListener;
import org.tctalent.anonymization.batch.listener.LoggingDocumentSkipListener;
import org.tctalent.anonymization.domain.entity.CandidateEntity;
import org.tctalent.anonymization.domain.document.CandidateDocument;
import org.tctalent.anonymization.model.IdentifiableCandidate;

/**
 * Batch configuration class for setting up the candidate migration job, including its steps,
 * readers, processors, and writers.
 * </p>
 * This class defines the Spring Batch beans required for the candidate migration process:
 * - A job to encapsulate the overall batch process
 * - A step to read, process, and write candidates
 * - Components for reading from the TC service or JPA repository, processing candidates into
 *   anonymised documents, and writing documents to MongoDB
 * </p>
 * Additional logging listeners are configured to monitor and log the batch progress at various
 * stages.
 *
 * @author sadatmalik
 */
@Configuration
@RequiredArgsConstructor
public class BatchConfig {

  private final BatchProperties batchProperties;

  /**
   * Configures the candidate migration job with its steps and completion listener.
   *
   * @param jobRepository the repository for storing job metadata
   * @param candidateRestToAuroraStep the step to execute the aurora db migration process
   * @param candidateRestToMongoStep the step to execute the mongo db migration process
   * @param listener the listener to handle job completion events
   * @return the configured candidate migration job
   */
  @Bean
  public Job candidateMigrationJob(JobRepository jobRepository,
      @Qualifier("candidateRestToAuroraStep") Step candidateRestToAuroraStep,
      @Qualifier("candidateRestToMongoStep") Step candidateRestToMongoStep,
      LoggingJobExecutionListener listener) {

    return new JobBuilder("candidateMigrationJob", jobRepository)
        .listener(listener)
        .start(candidateRestToAuroraStep) // First create the aurora anon db
        .next(candidateRestToMongoStep) // Then create the mongo anon db
        .build();
  }

  /**
   * Configures a candidate migration step to read candidates from the
   * {@link org.tctalent.anonymization.service.TalentCatalogService}, process them to anonymised
   * equivalent documents, and write them to AuroraDB.
   * <p>
   * The step is configured to be fault-tolerant, skipping {@link DataIntegrityViolationException}
   * and {@link ValidationException} when encountered, and applies a custom skip policy based on the
   * maximum number of allowed read skips.
   *
   * @param jobRepository the repository for storing step metadata
   * @param transactionManager the transaction manager for the step
   * @param tcItemReader the reader to fetch candidates from the talent catalog service
   * @param anonymousCandidateProcessor the processor to transform candidates into candidate entities
   * @param jpaItemWriter the writer to save candidate entities to AuroraDB
   * @param loggingChunkListener the listener for chunk-level logging
   * @param loggingRestReadListener the listener for item read-level logging
   * @param loggingRestToEntityProcessListener the listener for item process-level logging
   * @param loggingEntityWriteListener the listener for item write-level logging
   * @return the configured candidate migration step
   */
  @Bean
  @Qualifier("candidateRestToAuroraStep")
  public Step candidateRestToAuroraStep(JobRepository jobRepository,
      PlatformTransactionManager transactionManager,
      ItemReader<IdentifiableCandidate> tcItemReader,
      ItemProcessor<IdentifiableCandidate, CandidateEntity> anonymousCandidateProcessor,
      ItemWriter<CandidateEntity> jpaItemWriter,
      LoggingChunkListener loggingChunkListener,
      LoggingRestReadListener loggingRestReadListener,
      LoggingRestToEntityProcessListener loggingRestToEntityProcessListener,
      LoggingEntityWriteListener loggingEntityWriteListener,
      LoggingEntitySkipListener loggingEntitySkipListener) {

    return new StepBuilder("candidateRestToAuroraStep", jobRepository)
        .<IdentifiableCandidate, CandidateEntity>chunk(batchProperties.getChunkSize(), transactionManager)
        .reader(tcItemReader)
        .processor(anonymousCandidateProcessor)
        .writer(jpaItemWriter)
        .listener(loggingChunkListener)
        .listener(loggingRestReadListener)
        .listener(loggingRestToEntityProcessListener)
        .listener(loggingEntityWriteListener)
        .faultTolerant()
        .skip(DataIntegrityViolationException.class)
        .skip(ValidationException.class)
        .listener(loggingEntitySkipListener)
        .skipPolicy(new ConditionalSkipPolicy(batchProperties.getMaxReadSkips()))
        .build();
  }

  /**
   * Configures a candidate migration step that reads candidate records from the
   * {@link org.tctalent.anonymization.service.TalentCatalogService}, processes them into anonymised
   * {@link CandidateDocument} instances, and writes them to MongoDB.
   * <p>
   * This step uses a {@link ResourcelessTransactionManager} for transaction management since
   * MongoDB in this configuration does not support full transactions. The writer employs a
   * find-and-replace (upsert) strategy to check that if a document with the same publicId already
   * exists in the Mongo collection, it is completely replaced with the new document.
   * <p>
   * The step is configured to be fault-tolerant, skipping {@link CodecConfigurationException} when
   * encountered, and applies a custom skip policy based on the maximum number of allowed read skips.
   *
   * @param jobRepository the repository for storing step metadata
   * @param tcItemReader the reader to fetch candidate records from the Talent Catalog Service
   * @param candidateDocumentProcessor the processor to transform candidate records into {@link CandidateDocument} instances
   * @param mongoItemWriter the writer that persists {@link CandidateDocument} objects to MongoDB using a find-and-replace strategy
   * @param loggingChunkListener the listener for logging at the chunk level
   * @param loggingRestReadListener the listener for logging at the item read level
   * @param loggingRestToDocumentProcessListener the listener for logging during candidate-to-document processing
   * @param loggingDocumentWriteListener the listener for logging at the item write level
   * @param loggingDocumentSkipListener the listener for logging when an item is skipped during processing
   * @return the configured candidate migration step for writing to MongoDB
   */
  @Bean
  @Qualifier("candidateRestToMongoStep")
  public Step candidateRestToMongoStep(JobRepository jobRepository,
      ItemReader<IdentifiableCandidate> tcItemReader,
      ItemProcessor<IdentifiableCandidate, CandidateDocument> candidateDocumentProcessor,
      ItemWriter<CandidateDocument> mongoItemWriter,
      LoggingChunkListener loggingChunkListener,
      LoggingRestReadListener loggingRestReadListener,
      LoggingRestToDocumentProcessListener loggingRestToDocumentProcessListener,
      LoggingDocumentWriteListener loggingDocumentWriteListener,
      LoggingDocumentSkipListener loggingDocumentSkipListener) {

    return new StepBuilder("candidateRestToMongoStep", jobRepository)
        .<IdentifiableCandidate, CandidateDocument>chunk(batchProperties.getChunkSize(), new ResourcelessTransactionManager())
        .reader(tcItemReader)
        .processor(candidateDocumentProcessor)
        .writer(mongoItemWriter)
        .listener(loggingChunkListener)
        .listener(loggingRestReadListener)
        .listener(loggingRestToDocumentProcessListener)
        .listener(loggingDocumentWriteListener)
        .faultTolerant()
        .skip(CodecConfigurationException.class)
        .listener(loggingDocumentSkipListener)
        .skipPolicy(new ConditionalSkipPolicy(batchProperties.getMaxReadSkips()))
        .build();
  }

  @Bean
  @Qualifier("asyncJobLauncher")
  public JobLauncher asyncJobLauncher(JobRepository jobRepository) throws Exception {
    TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
    jobLauncher.setJobRepository(jobRepository);
    // Using an async executor so that jobLauncher.run() returns immediately.
    // See: https://docs.spring.io/spring-batch/reference/job/configuring-launcher.html
    jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
    jobLauncher.afterPropertiesSet();
    return jobLauncher;
  }

}
