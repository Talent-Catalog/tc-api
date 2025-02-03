package org.tctalent.anonymization.domain.document;

import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.entity.common.enums.Status;


@Getter
@Setter
public class Country {
  private String isoCode;
  private String name;
  private Status status;

  @Override
  public String toString() {
    return "Country{" + "name='" + getName() +
        "', isoCode='" + isoCode + '\'' +
        '}';
  }
}
