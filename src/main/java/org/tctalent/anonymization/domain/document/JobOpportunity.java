package org.tctalent.anonymization.domain.document;

import java.time.Instant;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.tctalent.anonymization.domain.common.JobOpportunityStage;

@Getter
@Setter
public class JobOpportunity {
  private Country country;
  private Employer employerEntity;
  private Boolean evergreen;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Instant publishedDate;

  private JobOpportunityStage stage;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate submissionDueDate;

  private Long hiringCommitment;
  private Boolean employerHiredInternationally;
}
