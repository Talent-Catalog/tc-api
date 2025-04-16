package org.tctalent.anonymization.controller;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchJobController {

  private final JobLauncher jobLauncher;
  private final Job candidateMigrationJob;

  /**
   * Launch a candidate anonymisation job.
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

      jobLauncher.run(candidateMigrationJob, params);

      return ResponseEntity.ok("Job '" + candidateMigrationJob.getName() + "' launched successfully.");

    } catch (Exception e) {
      throw new JobExecutionException("Job launch failed: " + e.getMessage());
    }
  }

}
