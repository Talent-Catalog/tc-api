package org.tctalent.anonymization.domain.document;

import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.model.Country;
import org.tctalent.anonymization.model.DocumentStatus;
import org.tctalent.anonymization.model.FamilyRelations;
import org.tctalent.anonymization.model.RiskLevel;
import org.tctalent.anonymization.model.YesNo;
import org.tctalent.anonymization.model.YesNoUnsure;

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
