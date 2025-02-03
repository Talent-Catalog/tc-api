package org.tctalent.anonymization.domain.document;


import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.entity.common.enums.EducationType;
import org.tctalent.anonymization.entity.mongo.EducationMajor;

@Getter
@Setter
public class CandidateEducation {
  private EducationType educationType;
  private Country country;
  private EducationMajor educationMajor;
  private Integer lengthOfCourseYears;
  private String institution;
  private String courseName;
  private Integer yearCompleted;
  private Boolean incomplete;
}
