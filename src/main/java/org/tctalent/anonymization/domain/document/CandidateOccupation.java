package org.tctalent.anonymization.domain.document;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CandidateOccupation {
  private Occupation occupation;
  private Integer yearsExperience;

  @Valid
  private List<@Valid CandidateJobExperience> candidateJobExperiences = new ArrayList<>();
}
