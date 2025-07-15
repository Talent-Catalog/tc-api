package org.tctalent.anonymization.domain.document;

import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.domain.common.DocumentStatus;
import org.tctalent.anonymization.domain.common.FamilyRelations;
import org.tctalent.anonymization.domain.common.RiskLevel;
import org.tctalent.anonymization.domain.common.YesNo;
import org.tctalent.anonymization.domain.common.YesNoUnsure;

@Getter
@Setter
public class CandidateVisaCheck {
  private Country country;
  private YesNo protection;
  private YesNo englishThreshold;
  private YesNo healthAssessment;
  private YesNo characterAssessment;
  private YesNo securityRisk;
  private RiskLevel overallRisk;
  private DocumentStatus validTravelDocs;
  private YesNoUnsure pathwayAssessment;
  private FamilyRelations destinationFamily;

  @Valid
  private List<@Valid CandidateVisaJobCheck> candidateVisaJobChecks;

}
