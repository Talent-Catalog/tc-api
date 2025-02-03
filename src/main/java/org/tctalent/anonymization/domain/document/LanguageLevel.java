package org.tctalent.anonymization.domain.document;

import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.model.CefrLevels;

@Getter
@Setter
public class LanguageLevel {
  private Integer level;
  private String name;
  private CefrLevels cefrLevel;
}
