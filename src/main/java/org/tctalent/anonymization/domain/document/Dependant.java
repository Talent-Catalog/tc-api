package org.tctalent.anonymization.domain.document;

import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.domain.common.Gender;
import org.tctalent.anonymization.domain.common.Registration;
import org.tctalent.anonymization.domain.common.YesNo;
import org.tctalent.anonymization.domain.common.DependantRelations;

@Getter
@Setter
public class Dependant {
  private DependantRelations relation;
  private String relationOther;
  private Integer yearOfBirth;
  private Gender gender;
  private Registration registered;
  private YesNo healthConcern;
}
