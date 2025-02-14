package org.tctalent.anonymization.domain.document;

import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.domain.common.Exam;

@Getter
@Setter
public class CandidateExam {
  private Exam exam;
  private String otherExam;
  private String score;
  private Long year;
  private String notes;
}
