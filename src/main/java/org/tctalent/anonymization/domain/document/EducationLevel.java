package org.tctalent.anonymization.domain.document;

import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.entity.common.enums.EducationType;
import org.tctalent.anonymization.entity.common.enums.Status;

@Getter
@Setter
public class EducationLevel {
  private Integer level;
  private Status status;
  private EducationType educationType;
}
