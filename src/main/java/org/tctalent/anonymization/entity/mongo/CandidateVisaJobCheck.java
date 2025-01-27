package org.tctalent.anonymization.entity.mongo;

import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.entity.common.enums.OtherVisas;
import org.tctalent.anonymization.entity.common.enums.TcEligibilityAssessment;
import org.tctalent.anonymization.entity.common.enums.VisaEligibility;
import org.tctalent.anonymization.entity.common.enums.YesNo;

@Getter
@Setter
public class CandidateVisaJobCheck {
  private JobOpportunity jobOpp;
  private YesNo interest;
  private String interestNotes;
  private YesNo qualification;
  private String qualificationNotes;
  private Occupation occupation;
  private String occupationNotes;
  private YesNo salaryTsmit;
  private YesNo regional;
  private YesNo eligible494;
  private String eligible494Notes;
  private YesNo eligible186;
  private String eligible186Notes;
  private OtherVisas eligibleOther;
  private String eligibleOtherNotes;
  private VisaEligibility putForward;
  private TcEligibilityAssessment tcEligibility;
  private String notes;
  private String relevantWorkExp;
  private String ageRequirement;
  private String preferredPathways;
  private String ineligiblePathways;
  private String eligiblePathways;
  private String occupationCategory;
  private String occupationSubCategory;
  private YesNo englishThreshold;

  @Valid
  private List<Long> languagesRequired;
  private YesNo languagesThresholdMet;
  private String languagesThresholdNotes;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CandidateVisaJobCheck {\n");
    sb.append("    jobOpp: ").append(toIndentedString(jobOpp)).append("\n");
    sb.append("    interest: ").append(toIndentedString(interest)).append("\n");
    sb.append("    interestNotes: ").append(toIndentedString(interestNotes)).append("\n");
    sb.append("    qualification: ").append(toIndentedString(qualification)).append("\n");
    sb.append("    qualificationNotes: ").append(toIndentedString(qualificationNotes)).append("\n");
    sb.append("    occupation: ").append(toIndentedString(occupation)).append("\n");
    sb.append("    occupationNotes: ").append(toIndentedString(occupationNotes)).append("\n");
    sb.append("    salaryTsmit: ").append(toIndentedString(salaryTsmit)).append("\n");
    sb.append("    regional: ").append(toIndentedString(regional)).append("\n");
    sb.append("    eligible494: ").append(toIndentedString(eligible494)).append("\n");
    sb.append("    eligible494Notes: ").append(toIndentedString(eligible494Notes)).append("\n");
    sb.append("    eligible186: ").append(toIndentedString(eligible186)).append("\n");
    sb.append("    eligible186Notes: ").append(toIndentedString(eligible186Notes)).append("\n");
    sb.append("    eligibleOther: ").append(toIndentedString(eligibleOther)).append("\n");
    sb.append("    eligibleOtherNotes: ").append(toIndentedString(eligibleOtherNotes)).append("\n");
    sb.append("    putForward: ").append(toIndentedString(putForward)).append("\n");
    sb.append("    tcEligibility: ").append(toIndentedString(tcEligibility)).append("\n");
    sb.append("    notes: ").append(toIndentedString(notes)).append("\n");
    sb.append("    relevantWorkExp: ").append(toIndentedString(relevantWorkExp)).append("\n");
    sb.append("    ageRequirement: ").append(toIndentedString(ageRequirement)).append("\n");
    sb.append("    preferredPathways: ").append(toIndentedString(preferredPathways)).append("\n");
    sb.append("    ineligiblePathways: ").append(toIndentedString(ineligiblePathways)).append("\n");
    sb.append("    eligiblePathways: ").append(toIndentedString(eligiblePathways)).append("\n");
    sb.append("    occupationCategory: ").append(toIndentedString(occupationCategory)).append("\n");
    sb.append("    occupationSubCategory: ").append(toIndentedString(occupationSubCategory)).append("\n");
    sb.append("    englishThreshold: ").append(toIndentedString(englishThreshold)).append("\n");
    sb.append("    languagesRequired: ").append(toIndentedString(languagesRequired)).append("\n");
    sb.append("    languagesThresholdMet: ").append(toIndentedString(languagesThresholdMet)).append("\n");
    sb.append("    languagesThresholdNotes: ").append(toIndentedString(languagesThresholdNotes)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
