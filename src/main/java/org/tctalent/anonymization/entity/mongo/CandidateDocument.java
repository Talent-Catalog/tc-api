package org.tctalent.anonymization.entity.mongo;

import jakarta.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.tctalent.anonymization.entity.common.enums.CandidateStatus;
import org.tctalent.anonymization.entity.common.enums.DocumentStatus;
import org.tctalent.anonymization.entity.common.enums.Gender;
import org.tctalent.anonymization.entity.common.enums.IeltsStatus;
import org.tctalent.anonymization.entity.common.enums.IntRecruitReason;
import org.tctalent.anonymization.entity.common.enums.LeftHomeReason;
import org.tctalent.anonymization.entity.common.enums.MaritalStatus;
import org.tctalent.anonymization.entity.common.enums.NotRegisteredStatus;
import org.tctalent.anonymization.entity.common.enums.ResidenceStatus;
import org.tctalent.anonymization.entity.common.enums.UnhcrStatus;
import org.tctalent.anonymization.entity.common.enums.VaccinationStatus;
import org.tctalent.anonymization.entity.common.enums.WorkPermit;
import org.tctalent.anonymization.entity.common.enums.YesNo;
import org.tctalent.anonymization.entity.common.enums.YesNoUnemployedOther;
import org.tctalent.anonymization.entity.common.enums.YesNoUnsure;
import org.tctalent.anonymization.entity.common.enums.AvailImmediateReason;

@Getter
@Setter
@Document(collection = "candidates")
public class CandidateDocument {

  private UUID publicId;

  private String candidateNumber;
  private String additionalInfo;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate asylumYear;
  private YesNoUnsure arrestImprison;
  private String arrestImprisonNotes;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate availDate;
  private YesNo availImmediate;
  private String availImmediateJobOps;
  private AvailImmediateReason availImmediateReason;
  private String availImmediateNotes;
  private Country birthCountry;

  @Valid
  private List<@Valid CandidateCertification> candidateCertifications;

  @Valid
  private List<@Valid CandidateCitizenship> candidateCitizenships;

  @Valid
  private List<@Valid Dependant> candidateDependants;

  @Valid
  private List<@Valid Destination> candidateDestinations;

  @Valid
  private List<@Valid CandidateEducation> candidateEducations;

  @Valid
  private List<@Valid CandidateExam> candidateExams;

  @Valid
  private List<@Valid CandidateJobExperience> candidateJobExperiences;

  @Valid
  private List<@Valid CandidateLanguage> candidateLanguages;

  // todo sm tested up to here - remove this comment after writing unit tests - placeholder
  private String candidateMessage;

  @Valid
  private List<@Valid CandidateNote> candidateNotes;

  @Valid
  private List<@Valid CandidateOccupation> candidateOccupations;

  @Valid
  private List<@Valid ModelList> candidateSavedLists;

  @Valid
  private List<@Valid CandidateSkill> candidateSkills;

  @Valid
  private List<@Valid CandidateVisaCheck> candidateVisaChecks;
  private YesNo canDrive;
  private String city;
  private YesNo conflict;
  private String conflictNotes;
  private Boolean contactConsentTcPartners;
  private Boolean contactConsentRegistration;
  private Country country;
  private YesNo covidVaccinated;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate covidVaccinatedDate;
  private String covidVaccineName;
  private String covidVaccineNotes;
  private VaccinationStatus covidVaccinatedStatus;
  private YesNoUnsure crimeConvict;
  private String crimeConvictNotes;
  private YesNo destLimit;
  private String destLimitNotes;
  private DocumentStatus drivingLicense;
  private Country drivingLicenseCountry;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate drivingLicenseExp;
  private String englishAssessment;
  private String englishAssessmentScoreIelts;
  private YesNo familyMove;
  private String familyMoveNotes;
  private String frenchAssessment;
  private Long frenchAssessmentScoreNclc;
  private User fullIntakeCompletedBy;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Instant fullIntakeCompletedDate;
  private Gender gender;
  private YesNo healthIssues;
  private String healthIssuesNotes;
  private String homeLocation;
  private String hostChallenges;
  private YesNo hostEntryLegally;
  private String hostEntryLegallyNotes;
  private Integer hostEntryYear;
  private String hostEntryYearNotes;
  private Float ieltsScore;

  @Valid
  private List<IntRecruitReason> intRecruitReasons;
  private String intRecruitOther;
  private YesNoUnsure intRecruitRural;
  private String intRecruitRuralNotes;
  private String leftHomeNotes;

  @Valid
  private List<LeftHomeReason> leftHomeReasons;
  private MaritalStatus maritalStatus;
  private String maritalStatusNotes;
  private EducationLevel maxEducationLevel;
  private String mediaWillingness;
  private EducationMajor migrationEducationMajor;
  private String migrationNationality;
  private YesNo militaryService;
  private YesNoUnsure militaryWanted;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate militaryStart;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate militaryEnd;
  private String militaryNotes;
  private User miniIntakeCompletedBy;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Instant miniIntakeCompletedDate;
  private YesNo monitoringEvaluationConsent;
  private Country nationality;
  private Long numberDependants;
  private YesNoUnsure partnerRegistered;

  @Valid
  private List<Long> partnerCitizenship;
  private EducationLevel partnerEduLevel;
  private String partnerEduLevelNotes;
  private YesNo partnerEnglish;
  private LanguageLevel partnerEnglishLevel;
  private IeltsStatus partnerIelts;
  private String partnerIeltsScore;
  private Long partnerIeltsYr;
  private Occupation partnerOccupation;
  private String partnerOccupationNotes;
  private ResidenceStatus residenceStatus;
  private String residenceStatusNotes;
  private YesNoUnsure returnedHome;
  private String returnedHomeReason;
  private String returnedHomeReasonNo;
  private YesNoUnsure returnHomeSafe;
  private YesNoUnsure returnHomeFuture;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate returnHomeWhen;
  private YesNo resettleThird;
  private String resettleThirdStatus;
  private String shareableNotes;
  private String state;
  private CandidateStatus status;
  private SurveyType surveyType;
  private String surveyComment;

  @Valid
  private List<@Valid TaskAssignment> taskAssignments;
  private YesNo unhcrConsent;
  private String unhcrNotes;
  private NotRegisteredStatus unhcrNotRegStatus;
  private YesNoUnsure unhcrRegistered;
  private UnhcrStatus unhcrStatus;
  private String unrwaNotes;
  private NotRegisteredStatus unrwaNotRegStatus;
  private YesNoUnsure unrwaRegistered;
  private YesNoUnsure visaIssues;
  private String visaIssuesNotes;
  private YesNoUnsure visaReject;
  private String visaRejectNotes;
  private YesNo workAbroad;
  private String workAbroadNotes;
  private YesNoUnemployedOther workDesired;
  private String workDesiredNotes;
  private WorkPermit workPermit;
  private YesNoUnsure workPermitDesired;
  private String workPermitDesiredNotes;
  private Integer yearOfArrival;
  private Integer yearOfBirth;
  private User createdBy;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Instant createdDate;
  private User updatedBy;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Instant updatedDate;

  @Override
  public int hashCode() {
    if (publicId != null) {
      return publicId.hashCode();
    } else {
      return super.hashCode();
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    CandidateDocument other = (CandidateDocument) obj;

    //If id is missing assume that it is not equal to other instance.
    if (publicId == null) return false;

    //Equivalence by id
    return publicId.equals(other.publicId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Candidate {\n");
    sb.append("    publicId: ").append(toIndentedString(publicId)).append("\n");
    sb.append("    additionalInfo: ").append(toIndentedString(additionalInfo)).append("\n");
    sb.append("    asylumYear: ").append(toIndentedString(asylumYear)).append("\n");
    sb.append("    arrestImprison: ").append(toIndentedString(arrestImprison)).append("\n");
    sb.append("    arrestImprisonNotes: ").append(toIndentedString(arrestImprisonNotes)).append("\n");
    sb.append("    availDate: ").append(toIndentedString(availDate)).append("\n");
    sb.append("    availImmediate: ").append(toIndentedString(availImmediate)).append("\n");
    sb.append("    availImmediateJobOps: ").append(toIndentedString(availImmediateJobOps)).append("\n");
    sb.append("    availImmediateReason: ").append(toIndentedString(availImmediateReason)).append("\n");
    sb.append("    availImmediateNotes: ").append(toIndentedString(availImmediateNotes)).append("\n");
    sb.append("    birthCountry: ").append(toIndentedString(birthCountry)).append("\n");
    sb.append("    candidateCertifications: ").append(toIndentedString(candidateCertifications)).append("\n");
    sb.append("    candidateCitizenships: ").append(toIndentedString(candidateCitizenships)).append("\n");
    sb.append("    candidateDependants: ").append(toIndentedString(candidateDependants)).append("\n");
    sb.append("    candidateDestinations: ").append(toIndentedString(candidateDestinations)).append("\n");
    sb.append("    candidateEducations: ").append(toIndentedString(candidateEducations)).append("\n");
    sb.append("    candidateExams: ").append(toIndentedString(candidateExams)).append("\n");
    sb.append("    candidateJobExperiences: ").append(toIndentedString(candidateJobExperiences)).append("\n");
    sb.append("    candidateLanguages: ").append(toIndentedString(candidateLanguages)).append("\n");
    sb.append("    candidateMessage: ").append(toIndentedString(candidateMessage)).append("\n");
    sb.append("    candidateNotes: ").append(toIndentedString(candidateNotes)).append("\n");
    sb.append("    candidateOccupations: ").append(toIndentedString(candidateOccupations)).append("\n");
    sb.append("    candidateSavedLists: ").append(toIndentedString(candidateSavedLists)).append("\n");
    sb.append("    candidateSkills: ").append(toIndentedString(candidateSkills)).append("\n");
    sb.append("    candidateVisaChecks: ").append(toIndentedString(candidateVisaChecks)).append("\n");
    sb.append("    canDrive: ").append(toIndentedString(canDrive)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    conflict: ").append(toIndentedString(conflict)).append("\n");
    sb.append("    conflictNotes: ").append(toIndentedString(conflictNotes)).append("\n");
    sb.append("    contactConsentTcPartners: ").append(toIndentedString(contactConsentTcPartners)).append("\n");
    sb.append("    contactConsentRegistration: ").append(toIndentedString(contactConsentRegistration)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    covidVaccinated: ").append(toIndentedString(covidVaccinated)).append("\n");
    sb.append("    covidVaccinatedDate: ").append(toIndentedString(covidVaccinatedDate)).append("\n");
    sb.append("    covidVaccineName: ").append(toIndentedString(covidVaccineName)).append("\n");
    sb.append("    covidVaccineNotes: ").append(toIndentedString(covidVaccineNotes)).append("\n");
    sb.append("    covidVaccinatedStatus: ").append(toIndentedString(covidVaccinatedStatus)).append("\n");
    sb.append("    crimeConvict: ").append(toIndentedString(crimeConvict)).append("\n");
    sb.append("    crimeConvictNotes: ").append(toIndentedString(crimeConvictNotes)).append("\n");
    sb.append("    destLimit: ").append(toIndentedString(destLimit)).append("\n");
    sb.append("    destLimitNotes: ").append(toIndentedString(destLimitNotes)).append("\n");
    sb.append("    drivingLicense: ").append(toIndentedString(drivingLicense)).append("\n");
    sb.append("    drivingLicenseCountry: ").append(toIndentedString(drivingLicenseCountry)).append("\n");
    sb.append("    drivingLicenseExp: ").append(toIndentedString(drivingLicenseExp)).append("\n");
    sb.append("    englishAssessment: ").append(toIndentedString(englishAssessment)).append("\n");
    sb.append("    englishAssessmentScoreIelts: ").append(toIndentedString(englishAssessmentScoreIelts)).append("\n");
    sb.append("    familyMove: ").append(toIndentedString(familyMove)).append("\n");
    sb.append("    familyMoveNotes: ").append(toIndentedString(familyMoveNotes)).append("\n");
    sb.append("    frenchAssessment: ").append(toIndentedString(frenchAssessment)).append("\n");
    sb.append("    frenchAssessmentScoreNclc: ").append(toIndentedString(frenchAssessmentScoreNclc)).append("\n");
    sb.append("    fullIntakeCompletedBy: ").append(toIndentedString(fullIntakeCompletedBy)).append("\n");
    sb.append("    fullIntakeCompletedDate: ").append(toIndentedString(fullIntakeCompletedDate)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    healthIssues: ").append(toIndentedString(healthIssues)).append("\n");
    sb.append("    healthIssuesNotes: ").append(toIndentedString(healthIssuesNotes)).append("\n");
    sb.append("    homeLocation: ").append(toIndentedString(homeLocation)).append("\n");
    sb.append("    hostChallenges: ").append(toIndentedString(hostChallenges)).append("\n");
    sb.append("    hostEntryLegally: ").append(toIndentedString(hostEntryLegally)).append("\n");
    sb.append("    hostEntryLegallyNotes: ").append(toIndentedString(hostEntryLegallyNotes)).append("\n");
    sb.append("    hostEntryYear: ").append(toIndentedString(hostEntryYear)).append("\n");
    sb.append("    hostEntryYearNotes: ").append(toIndentedString(hostEntryYearNotes)).append("\n");
    sb.append("    ieltsScore: ").append(toIndentedString(ieltsScore)).append("\n");
    sb.append("    intRecruitReasons: ").append(toIndentedString(intRecruitReasons)).append("\n");
    sb.append("    intRecruitOther: ").append(toIndentedString(intRecruitOther)).append("\n");
    sb.append("    intRecruitRural: ").append(toIndentedString(intRecruitRural)).append("\n");
    sb.append("    intRecruitRuralNotes: ").append(toIndentedString(intRecruitRuralNotes)).append("\n");
    sb.append("    leftHomeNotes: ").append(toIndentedString(leftHomeNotes)).append("\n");
    sb.append("    leftHomeReasons: ").append(toIndentedString(leftHomeReasons)).append("\n");
    sb.append("    maritalStatus: ").append(toIndentedString(maritalStatus)).append("\n");
    sb.append("    maritalStatusNotes: ").append(toIndentedString(maritalStatusNotes)).append("\n");
    sb.append("    maxEducationLevel: ").append(toIndentedString(maxEducationLevel)).append("\n");
    sb.append("    mediaWillingness: ").append(toIndentedString(mediaWillingness)).append("\n");
    sb.append("    migrationEducationMajor: ").append(toIndentedString(migrationEducationMajor)).append("\n");
    sb.append("    migrationNationality: ").append(toIndentedString(migrationNationality)).append("\n");
    sb.append("    militaryService: ").append(toIndentedString(militaryService)).append("\n");
    sb.append("    militaryWanted: ").append(toIndentedString(militaryWanted)).append("\n");
    sb.append("    militaryStart: ").append(toIndentedString(militaryStart)).append("\n");
    sb.append("    militaryEnd: ").append(toIndentedString(militaryEnd)).append("\n");
    sb.append("    militaryNotes: ").append(toIndentedString(militaryNotes)).append("\n");
    sb.append("    miniIntakeCompletedBy: ").append(toIndentedString(miniIntakeCompletedBy)).append("\n");
    sb.append("    miniIntakeCompletedDate: ").append(toIndentedString(miniIntakeCompletedDate)).append("\n");
    sb.append("    monitoringEvaluationConsent: ").append(toIndentedString(monitoringEvaluationConsent)).append("\n");
    sb.append("    nationality: ").append(toIndentedString(nationality)).append("\n");
    sb.append("    numberDependants: ").append(toIndentedString(numberDependants)).append("\n");
    sb.append("    partnerRegistered: ").append(toIndentedString(partnerRegistered)).append("\n");
    sb.append("    partnerCitizenship: ").append(toIndentedString(partnerCitizenship)).append("\n");
    sb.append("    partnerEduLevel: ").append(toIndentedString(partnerEduLevel)).append("\n");
    sb.append("    partnerEduLevelNotes: ").append(toIndentedString(partnerEduLevelNotes)).append("\n");
    sb.append("    partnerEnglish: ").append(toIndentedString(partnerEnglish)).append("\n");
    sb.append("    partnerEnglishLevel: ").append(toIndentedString(partnerEnglishLevel)).append("\n");
    sb.append("    partnerIelts: ").append(toIndentedString(partnerIelts)).append("\n");
    sb.append("    partnerIeltsScore: ").append(toIndentedString(partnerIeltsScore)).append("\n");
    sb.append("    partnerIeltsYr: ").append(toIndentedString(partnerIeltsYr)).append("\n");
    sb.append("    partnerOccupation: ").append(toIndentedString(partnerOccupation)).append("\n");
    sb.append("    partnerOccupationNotes: ").append(toIndentedString(partnerOccupationNotes)).append("\n");
    sb.append("    residenceStatus: ").append(toIndentedString(residenceStatus)).append("\n");
    sb.append("    residenceStatusNotes: ").append(toIndentedString(residenceStatusNotes)).append("\n");
    sb.append("    returnedHome: ").append(toIndentedString(returnedHome)).append("\n");
    sb.append("    returnedHomeReason: ").append(toIndentedString(returnedHomeReason)).append("\n");
    sb.append("    returnedHomeReasonNo: ").append(toIndentedString(returnedHomeReasonNo)).append("\n");
    sb.append("    returnHomeSafe: ").append(toIndentedString(returnHomeSafe)).append("\n");
    sb.append("    returnHomeFuture: ").append(toIndentedString(returnHomeFuture)).append("\n");
    sb.append("    returnHomeWhen: ").append(toIndentedString(returnHomeWhen)).append("\n");
    sb.append("    resettleThird: ").append(toIndentedString(resettleThird)).append("\n");
    sb.append("    resettleThirdStatus: ").append(toIndentedString(resettleThirdStatus)).append("\n");
    sb.append("    shareableNotes: ").append(toIndentedString(shareableNotes)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    surveyType: ").append(toIndentedString(surveyType)).append("\n");
    sb.append("    surveyComment: ").append(toIndentedString(surveyComment)).append("\n");
    sb.append("    taskAssignments: ").append(toIndentedString(taskAssignments)).append("\n");
    sb.append("    unhcrConsent: ").append(toIndentedString(unhcrConsent)).append("\n");
    sb.append("    unhcrNotes: ").append(toIndentedString(unhcrNotes)).append("\n");
    sb.append("    unhcrNotRegStatus: ").append(toIndentedString(unhcrNotRegStatus)).append("\n");
    sb.append("    unhcrRegistered: ").append(toIndentedString(unhcrRegistered)).append("\n");
    sb.append("    unhcrStatus: ").append(toIndentedString(unhcrStatus)).append("\n");
    sb.append("    unrwaNotes: ").append(toIndentedString(unrwaNotes)).append("\n");
    sb.append("    unrwaNotRegStatus: ").append(toIndentedString(unrwaNotRegStatus)).append("\n");
    sb.append("    unrwaRegistered: ").append(toIndentedString(unrwaRegistered)).append("\n");
    sb.append("    visaIssues: ").append(toIndentedString(visaIssues)).append("\n");
    sb.append("    visaIssuesNotes: ").append(toIndentedString(visaIssuesNotes)).append("\n");
    sb.append("    visaReject: ").append(toIndentedString(visaReject)).append("\n");
    sb.append("    visaRejectNotes: ").append(toIndentedString(visaRejectNotes)).append("\n");
    sb.append("    workAbroad: ").append(toIndentedString(workAbroad)).append("\n");
    sb.append("    workAbroadNotes: ").append(toIndentedString(workAbroadNotes)).append("\n");
    sb.append("    workDesired: ").append(toIndentedString(workDesired)).append("\n");
    sb.append("    workDesiredNotes: ").append(toIndentedString(workDesiredNotes)).append("\n");
    sb.append("    workPermit: ").append(toIndentedString(workPermit)).append("\n");
    sb.append("    workPermitDesired: ").append(toIndentedString(workPermitDesired)).append("\n");
    sb.append("    workPermitDesiredNotes: ").append(toIndentedString(workPermitDesiredNotes)).append("\n");
    sb.append("    yearOfArrival: ").append(toIndentedString(yearOfArrival)).append("\n");
    sb.append("    yearOfBirth: ").append(toIndentedString(yearOfBirth)).append("\n");
    sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
    sb.append("    createdDate: ").append(toIndentedString(createdDate)).append("\n");
    sb.append("    updatedBy: ").append(toIndentedString(updatedBy)).append("\n");
    sb.append("    updatedDate: ").append(toIndentedString(updatedDate)).append("\n");
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

