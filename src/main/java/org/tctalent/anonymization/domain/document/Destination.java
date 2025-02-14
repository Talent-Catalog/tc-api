package org.tctalent.anonymization.domain.document;

import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.domain.common.YesNoUnsure;

@Getter
@Setter
public class Destination {
  private Country country;
  private YesNoUnsure interest;
  private String notes;
}
