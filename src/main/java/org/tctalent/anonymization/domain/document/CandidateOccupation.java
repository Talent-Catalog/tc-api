package org.tctalent.anonymization.domain.document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateOccupation {
  private Occupation occupation;
  private Integer yearsExperience;
}
