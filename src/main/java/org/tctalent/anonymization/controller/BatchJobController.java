package org.tctalent.anonymization.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tctalent.anonymization.service.BatchJobService;


/**
 * REST controller that exposes endpoints for managing and running batch jobs.
 * <p>
 * Provides endpoints to launch a candidate anonymisation job and retrieve job executions.
 * <p>
 * Access to endpoints is secured with an 'ADMIN' authority.
 *
 * @author sadatmalik
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/batch")
public class BatchJobController {

  private final BatchJobService batchJobService;

  /**
   * Launches the candidate anonymisation batch job (one run per day).
   *
   * @return a ResponseEntity with a success message if the job is launched successfully
   * @throws JobExecutionException if the job launch fails
   */
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/jobs/run")
  public ResponseEntity<String> runJob() throws Exception{
    String message = batchJobService.runCandidateMigrationJob();
    return ResponseEntity.ok(message);
  }

  /**
   * Returns a plain-text list of recent job executions.
   *
   * @return a list of JobExecutionInfo DTOs
   */
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/jobs")
  public ResponseEntity<String> getJobExecutions() {
    String summary = batchJobService.getJobExecutionsSummary();
    return ResponseEntity.ok(summary);
  }

  /**
   * Attempts to stop a running job execution.
   *
   * @param executionId the ID of the job execution to stop
   * @return a ResponseEntity containing a message indicating whether the stop request was initiated successfully.
   * @throws Exception if the stop operation fails (e.g. invalid execution ID or internal error)
   */
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/jobs/{executionId}/stop")
  public ResponseEntity<String> stopJobExecution(@PathVariable Long executionId) throws Exception {
    String message = batchJobService.stopJobExecution(executionId);
    return ResponseEntity.ok(message);
  }

}
