package org.tctalent.anonymization.domain.document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employer {
  private Country country;
  private Boolean hasHiredInternationally;
}
