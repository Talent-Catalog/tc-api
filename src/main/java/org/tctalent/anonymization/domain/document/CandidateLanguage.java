package org.tctalent.anonymization.domain.document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateLanguage {
  private Language language;
  private LanguageLevel writtenLevel;
  private LanguageLevel spokenLevel;
}
