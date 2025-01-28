package org.tctalent.anonymization.entity.mongo;

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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Dependant {\n");
    sb.append("    relation: ").append(toIndentedString(relation)).append("\n");
    sb.append("    relationOther: ").append(toIndentedString(relationOther)).append("\n");
    sb.append("    yearOfBirth: ").append(toIndentedString(yearOfBirth)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    registered: ").append(toIndentedString(registered)).append("\n");
    sb.append("    healthConcern: ").append(toIndentedString(healthConcern)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
