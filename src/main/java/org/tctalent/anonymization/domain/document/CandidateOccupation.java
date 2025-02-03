package org.tctalent.anonymization.domain.document;

import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.entity.mongo.Occupation;

@Getter
@Setter
public class CandidateOccupation {
  private Occupation occupation;
  private Integer yearsExperience;
}
