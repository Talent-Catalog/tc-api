package org.tctalent.anonymization.batch;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.tctalent.anonymization.domain.entity.AnonymousCandidate;
import org.tctalent.anonymization.entity.db.Candidate;
import org.tctalent.anonymization.entity.mongo.CandidateDocument;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.repository.CandidateAuroraRepository;
import org.tctalent.anonymization.repository.CandidateMongoRepository;
import org.tctalent.anonymization.repository.CandidateRepository;

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
   * @param candidateMigrationStep the step to execute the candidate migration process
   * @param listener the listener to handle job completion events
   * @return the configured candidate migration job
   */
  @Bean
  public Job candidateMigrationJob(JobRepository jobRepository,
      @Qualifier("candidateRestMigrationStep") Step candidateMigrationStep,
      JobCompletionNotificationListener listener) {

    return new JobBuilder("candidateMigrationJob", jobRepository)
        .listener(listener)
        .start(candidateMigrationStep)
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
   * @param itemProcessor the processor to transform candidates into candidate documents
   * @param mongoItemWriter the writer to save candidate documents to MongoDB
   * @param jpaItemWriter the writer to save candidate entities to AuroraDB
   * @param loggingChunkListener the listener for chunk-level logging
   * @param loggingItemReaderListener the listener for item read-level logging
   * @param loggingItemProcessListener the listener for item process-level logging
   * @param loggingItemWriterListener the listener for item write-level logging
   * @return the configured candidate migration step
   */
  @Bean
  @Qualifier("candidateRestMigrationStep")
  // todo batch to both aurora and mongo
  public Step candidateRestMigrationStep(JobRepository jobRepository,
      PlatformTransactionManager transactionManager, // Use JPA transaction manager
      ItemReader<IdentifiableCandidate> tcItemReader,
//      ItemProcessor<IdentifiableCandidate, CandidateDocument> itemProcessor,
      ItemProcessor<IdentifiableCandidate, AnonymousCandidate> itemProcessor,
      ItemWriter<CandidateDocument> mongoItemWriter,
      ItemWriter<AnonymousCandidate> jpaItemWriter,
      LoggingChunkListener loggingChunkListener,
      LoggingItemReaderListener loggingItemReaderListener,
      LoggingItemProcessListener loggingItemProcessListener,
      LoggingItemWriterListener loggingItemWriterListener) {

    return new StepBuilder("candidateRestMigrationStep", jobRepository)
//        .<IdentifiableCandidate, CandidateDocument>chunk(batchProperties.getChunkSize(), transactionManager)
        .<IdentifiableCandidate, AnonymousCandidate>chunk(batchProperties.getChunkSize(), transactionManager)
        .reader(tcItemReader)
        .processor(itemProcessor)
//        .writer(mongoItemWriter)
        .writer(jpaItemWriter)
        .listener(loggingChunkListener)
        .listener(loggingItemReaderListener)
        .listener(loggingItemProcessListener)
        .listener(loggingItemWriterListener)
        .faultTolerant()
        .skipPolicy(new ConditionalSkipPolicy(batchProperties.getMaxReadSkips()))
        .build();
  }

  /**
   * Configures an ItemReader to fetch paginated candidates from the JPA repository.
   *
   * @param candidateRepository the repository used to retrieve candidates
   * @return an ItemReader for reading candidates from the JPA repository
   */
  @Bean
  public ItemReader<Candidate> jpaItemReader(CandidateRepository candidateRepository) {
    return new RepositoryItemReaderBuilder<Candidate>()
        .name("candidateJpaRepositoryReader")
        .repository(candidateRepository)
        .methodName("findAll")
        .pageSize(batchProperties.getPageSize())
        .arguments(Collections.emptyList())
        .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
        .build();
  }

  /**
   * Configures an ItemWriter to save CandidateDocument objects to the MongoDB repository.
   *
   * @param candidateRepository the repository used to persist CandidateDocument objects
   * @return an ItemWriter for writing CandidateDocument objects to MongoDB
   */
  @Bean
  public ItemWriter<CandidateDocument> mongoItemWriter(CandidateMongoRepository candidateRepository) {
    return new RepositoryItemWriterBuilder<CandidateDocument>()
        .repository(candidateRepository)
        .methodName("save") // Implicitly throttles the batch, which is preferred. Use "saveAll" if performance is an issue.
        .build();
  }

  // todo javadoc
  @Bean
  public ItemWriter<AnonymousCandidate> jpaItemWriter(
      CandidateAuroraRepository candidateAuroraRepository) {
    return new RepositoryItemWriterBuilder<AnonymousCandidate>()
        .repository(candidateAuroraRepository)
        .methodName("save")
        .build();
  }

}
