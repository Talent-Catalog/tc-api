package org.tctalent.anonymization.batch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.tctalent.anonymization.batch.listener.LoggingDocumentWriteListener;
import org.tctalent.anonymization.batch.listener.LoggingJobExecutionListener;
import org.tctalent.anonymization.batch.listener.LoggingChunkListener;
import org.tctalent.anonymization.batch.listener.LoggingRestToDocumentProcessListener;
import org.tctalent.anonymization.batch.listener.LoggingRestToEntityProcessListener;
import org.tctalent.anonymization.batch.listener.LoggingRestReadListener;
import org.tctalent.anonymization.batch.listener.LoggingEntityWriteListener;
import org.tctalent.anonymization.domain.entity.CandidateEntity;
import org.tctalent.anonymization.domain.document.CandidateDocument;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.repository.CandidateEntityRepository;
import org.tctalent.anonymization.repository.CandidateDocumentRepository;

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
   * equivalent documents, and write them to MongoDB.
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
      LoggingEntityWriteListener loggingEntityWriteListener) {

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
        .skipPolicy(new ConditionalSkipPolicy(batchProperties.getMaxReadSkips()))
        .build();
  }

  // todo java doc
  @Bean
  @Qualifier("candidateRestToMongoStep")
  public Step candidateRestToMongoStep(JobRepository jobRepository,
      ItemReader<IdentifiableCandidate> tcItemReader,
      ItemProcessor<IdentifiableCandidate, CandidateDocument> candidateDocumentProcessor,
      ItemWriter<CandidateDocument> mongoItemWriter,
      LoggingChunkListener loggingChunkListener,
      LoggingRestReadListener loggingRestReadListener,
      LoggingRestToDocumentProcessListener loggingRestToDocumentProcessListener,
      LoggingDocumentWriteListener loggingDocumentWriteListener) {

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
        .skipPolicy(new ConditionalSkipPolicy(batchProperties.getMaxReadSkips()))
        .build();
  }

  // todo javadoc
  @Bean
  public ItemWriter<CandidateEntity> jpaItemWriter(
      CandidateEntityRepository candidateEntityRepository) {
    return new RepositoryItemWriterBuilder<CandidateEntity>()
        .repository(candidateEntityRepository)
        .methodName("save")
        .build();
  }

  /**
   * Configures an ItemWriter to save CandidateDocument objects to the MongoDB repository.
   *
   * @param candidateDocumentRepository the repository used to persist CandidateDocument objects
   * @return an ItemWriter for writing CandidateDocument objects to MongoDB
   */
  @Bean
  public ItemWriter<CandidateDocument> mongoItemWriter(
      CandidateDocumentRepository candidateDocumentRepository) {
    return new RepositoryItemWriterBuilder<CandidateDocument>()
        .repository(candidateDocumentRepository)
        .methodName("save") // Implicitly throttles the batch, which is preferred. Use "saveAll" if performance is an issue.
        .build();
  }

}
