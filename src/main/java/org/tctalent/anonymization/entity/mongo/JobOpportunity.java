package org.tctalent.anonymization.entity.mongo;

import jakarta.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.tctalent.anonymization.domain.document.Country;
import org.tctalent.anonymization.domain.common.JobOpportunityStage;
import org.tctalent.anonymization.domain.document.Employer;

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
