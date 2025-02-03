package org.tctalent.anonymization.domain.document;

import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.entity.common.enums.Gender;
import org.tctalent.anonymization.entity.common.enums.Registration;
import org.tctalent.anonymization.entity.common.enums.YesNo;
import org.tctalent.anonymization.entity.common.enums.DependantRelations;

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
