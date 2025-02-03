package org.tctalent.anonymization.domain.document;

import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.entity.common.enums.NoteType;


// todo - sm - remove all candidate notes - potential data leak
@Getter
@Setter
public class CandidateNote {
  private String title;
  private String comment;
  private NoteType noteType;
}

