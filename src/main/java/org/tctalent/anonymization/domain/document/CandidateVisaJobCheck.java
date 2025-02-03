package org.tctalent.anonymization.domain.document;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.model.JobOpportunity;
import org.tctalent.anonymization.model.Occupation;
import org.tctalent.anonymization.model.OtherVisas;
import org.tctalent.anonymization.model.TcEligibilityAssessment;
import org.tctalent.anonymization.model.VisaEligibility;
import org.tctalent.anonymization.model.YesNo;

@Getter
@Setter
public class CandidateVisaJobCheck {
  private JobOpportunity jobOpp;
  private YesNo interest;
  private YesNo qualification;
  private Occupation occupation;
  private YesNo salaryTsmit;
  private YesNo regional;
  private YesNo eligible494;
  private YesNo eligible186;
  private OtherVisas eligibleOther;
  private VisaEligibility putForward;
  private TcEligibilityAssessment tcEligibility;
  private String ageRequirement;

  @Valid
  private List<Long> languagesRequired = new ArrayList<>();

  private YesNo languagesThresholdMet;

}
