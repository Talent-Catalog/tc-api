package org.tctalent.anonymization.service;

import org.springframework.batch.core.JobExecutionException;

/**
 * Service interface for managing and running Spring Batch jobs.
 * <p>
 * Provides operations to launch the candidate anonymisation job and to retrieve a plain-text
 * summary of recent job executions.
 * </p>
 */
public interface BatchJobService {

  /**
   * Launches the candidateMigrationJob.
   * Returns a confirmation message or throws on failure.
   */
  String runCandidateMigrationJob() throws JobExecutionException;

  /**
   * Builds a plain-text summary of job executions.
   */
  String getJobExecutionsSummary();

  /**
   * Attempts to stop a running job execution by ID.
   */
  String stopJobExecution(Long executionId) throws Exception;

  /**
   * Attempts to restart a job execution by ID.
   */
  String restartJobExecution(Long executionId) throws Exception;

}
