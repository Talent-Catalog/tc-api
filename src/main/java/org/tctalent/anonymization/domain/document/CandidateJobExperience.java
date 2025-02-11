package org.tctalent.anonymization.domain.document;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class CandidateJobExperience {
  private Country country;
  private String companyName;
  private String role;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate startDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate endDate;

  private Boolean fullTime;
  private Boolean paid;
  private String description;
}
