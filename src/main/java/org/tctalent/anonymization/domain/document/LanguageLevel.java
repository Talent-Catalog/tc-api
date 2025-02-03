package org.tctalent.anonymization.domain.document;

import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.model.CefrLevels;

@Getter
@Setter
public class LanguageLevel {
  private Integer level;
  private String name;
  // todo - sm - add status - it's on the entity but check if it's on the API schema
  private CefrLevels cefrLevel;
}
