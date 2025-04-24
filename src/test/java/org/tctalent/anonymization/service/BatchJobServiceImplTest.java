package org.tctalent.anonymization.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class BatchJobServiceImplTest {

  @Mock private JobLauncher asyncJobLauncher;
  @Mock private Job candidateMigrationJob;
  @Mock private JobExplorer jobExplorer;

  @InjectMocks private BatchJobServiceImpl service;

  @BeforeEach
  void setup() {
    when(candidateMigrationJob.getName()).thenReturn("candidateMigrationJob");
  }

  @Test
  @DisplayName("Run candidate migration job")
  void runCandidateMigrationJob_success() throws Exception {
    // When
    String result = service.runCandidateMigrationJob();

    // Then
    assertEquals("Job 'candidateMigrationJob' launched successfully.", result);

    // And verify that we passed a 'jobDate' param equal to today
    ArgumentCaptor<JobParameters> captor = ArgumentCaptor.forClass(JobParameters.class);
    verify(asyncJobLauncher).run(eq(candidateMigrationJob), captor.capture());

    JobParameters params = captor.getValue();
    assertTrue(params.getParameters().containsKey("jobDate"));
    String dateParam = params.getString("jobDate");
    assertEquals(LocalDate.now().toString(), dateParam);
  }

  @Test
  @DisplayName("Run candidate migration job fails")
  void runCandidateMigrationJob_failure() throws Exception {
    // Given
    when(asyncJobLauncher
        .run(eq(candidateMigrationJob), any(JobParameters.class)))
        .thenThrow(new RuntimeException("boom!"));

    // When / Then
    JobExecutionException ex =
        assertThrows(JobExecutionException.class, () -> service.runCandidateMigrationJob());
    assertTrue(ex.getMessage().contains("Job launch failed: boom!"));
  }

  @Test
  @DisplayName("Get job execution summary")
  void getJobExecutionsSummary_withExecutions() {
    // Given
    when(jobExplorer.getJobNames()).thenReturn(List.of("jobA"));
    JobInstance instance = new JobInstance(10L, "jobA");
    when(jobExplorer.getJobInstances("jobA", 0, 20))
        .thenReturn(List.of(instance));

    JobExecution exec = new JobExecution(instance, null);
    exec.setId(100L);
    exec.setStatus(BatchStatus.COMPLETED);
    LocalDateTime start = LocalDateTime.of(2025, 4, 16, 12, 0);
    LocalDateTime end   = LocalDateTime.of(2025, 4, 16, 12, 30);
    exec.setStartTime(start);
    exec.setEndTime(end);

    when(jobExplorer.getJobExecutions(instance))
        .thenReturn(List.of(exec));

    // When
    String summary = service.getJobExecutionsSummary();

    // Then
    String expectedLine = String.format(
        "[%d] %s %s %s - %s",
        100L, "jobA", "COMPLETED", start.toString(), end.toString()
    );
    assertTrue(summary.contains(expectedLine));
    assertTrue(summary.endsWith("\n")); // Check each line ends with a newline
  }

  @Test
  @DisplayName("Get job execution summary empty")
  void getJobExecutionsSummary_empty() {
    // Given no job names
    when(jobExplorer.getJobNames()).thenReturn(Collections.emptyList());

    // When
    String summary = service.getJobExecutionsSummary();

    // Then
    assertEquals("No job executions found.", summary);
  }

}
