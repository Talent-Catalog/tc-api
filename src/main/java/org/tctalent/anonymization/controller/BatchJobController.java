package org.tctalent.anonymization.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller that exposes endpoints for managing and running batch jobs.
 * <p>
 * Provides an endpoint to launch a candidate anonymisation job. Only one job instance runs per day
 * by including the current date as a unique job parameter. Access to the endpoint is secured with
 * an 'ADMIN' authority.
 *
 * @author sadatmalik
 */
@RestController
@RequestMapping("/batch")
public class BatchJobController {

  private final JobLauncher asyncJobLauncher;
  private final Job candidateMigrationJob;
  private final JobExplorer jobExplorer;


  public BatchJobController(
      @Qualifier("asyncJobLauncher") JobLauncher asyncJobLauncher,
      @Qualifier("candidateMigrationJob") Job candidateMigrationJob,
      JobExplorer jobExplorer) {

    this.asyncJobLauncher = asyncJobLauncher;
    this.candidateMigrationJob = candidateMigrationJob;
    this.jobExplorer = jobExplorer;
  }

  /**
   * Launches the candidate anonymisation batch job.
   * <p>
   * A unique job parameter "jobDate" is added based on the current date to enforce a single job
   * instance per day. The endpoint is secured and only accessible to users with the 'ADMIN'
   * authority.
   *
   * @return a ResponseEntity with a success message if the job is launched successfully
   * @throws JobExecutionException if the job launch fails
   */
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/jobs/run")
  public ResponseEntity<String> runJob() throws Exception{
    try {
      // Job params must be unique per job execution, if not the job will not run
      // We enforce a max of one job instance per day by setting a date parameter
      JobParameters params = new JobParametersBuilder()
          .addString("jobDate", LocalDate.now().toString()) // e.g., "2025-04-15"
          .toJobParameters();

      asyncJobLauncher.run(candidateMigrationJob, params);

      return ResponseEntity.ok("Job '" + candidateMigrationJob.getName() + "' launched successfully.");

    } catch (Exception e) {
      throw new JobExecutionException("Job launch failed: " + e.getMessage());
    }
  }

  /**
   * Retrieves summaries of recent batch job executions across all job instances.
   * Each entry contains execution ID, job name, status, start/end times, and parameters.
   *
   * @return a list of JobExecutionInfo DTOs
   */
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/jobs")
  public ResponseEntity<String> getJobExecutions() {
    StringBuilder sb = new StringBuilder();

    for (String jobName : jobExplorer.getJobNames()) {
      // fetch up to 20 recent instances
      List<JobInstance> instances = jobExplorer.getJobInstances(jobName, 0, 20);
      for (JobInstance instance : instances) {
        for (JobExecution exec : jobExplorer.getJobExecutions(instance)) {
          sb.append(formatExecution(exec));
        }
      }
    }

    String result = !sb.isEmpty() ? sb.toString() : "No job executions found.\n";
    return ResponseEntity.ok(result);
  }

  /**
   * Formats a single JobExecution as: [<executionId>] <jobName> <status> <startTime> - <endTime>
   */
  private String formatExecution(JobExecution exec) {
    return "["
        + exec.getId()
        + "] "
        + exec.getJobInstance().getJobName()
        + " "
        + exec.getStatus()
        + " "
        + formatDate(exec.getStartTime())
        + " - "
        + formatDate(exec.getEndTime())
        + "\n";
  }

  private String formatDate(LocalDateTime date) {
    return date == null
        ? "null"
        : date.toString();
  }

}
