package org.tctalent.anonymization.domain.document;

import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.domain.common.Status;


@Getter
@Setter
public class EducationMajor {
  private Long id;
  private String iscedCode;
  private String name;
  private Status status;
}
