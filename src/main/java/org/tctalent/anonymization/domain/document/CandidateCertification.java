package org.tctalent.anonymization.domain.document;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateCertification {
  private String name;
  private String institution;
  private LocalDate dateCompleted;
}
