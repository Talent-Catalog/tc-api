package org.tctalent.anonymization.domain.document;

import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.model.Language;
import org.tctalent.anonymization.model.LanguageLevel;

@Getter
@Setter
public class CandidateLanguage {
  private Language language;
  private LanguageLevel writtenLevel;
  private LanguageLevel spokenLevel;
}
