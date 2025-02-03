package org.tctalent.anonymization.domain.document;

import jakarta.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.tctalent.anonymization.model.AvailImmediateReason;
import org.tctalent.anonymization.model.CandidateCertification;
import org.tctalent.anonymization.model.CandidateCitizenship;
import org.tctalent.anonymization.model.CandidateEducation;
import org.tctalent.anonymization.model.CandidateExam;
import org.tctalent.anonymization.model.CandidateJobExperience;
import org.tctalent.anonymization.model.CandidateLanguage;
import org.tctalent.anonymization.model.CandidateNote;
import org.tctalent.anonymization.model.CandidateOccupation;
import org.tctalent.anonymization.model.CandidateSkill;
import org.tctalent.anonymization.model.CandidateStatus;
import org.tctalent.anonymization.model.CandidateVisaCheck;
import org.tctalent.anonymization.model.Country;
import org.tctalent.anonymization.model.Dependant;
import org.tctalent.anonymization.model.Destination;
import org.tctalent.anonymization.model.DocumentStatus;
import org.tctalent.anonymization.model.EducationLevel;
import org.tctalent.anonymization.model.EducationMajor;
import org.tctalent.anonymization.model.Gender;
import org.tctalent.anonymization.model.IeltsStatus;
import org.tctalent.anonymization.model.IntRecruitReason;
import org.tctalent.anonymization.model.LanguageLevel;
import org.tctalent.anonymization.model.LeftHomeReason;
import org.tctalent.anonymization.model.MaritalStatus;
import org.tctalent.anonymization.model.NotRegisteredStatus;
import org.tctalent.anonymization.model.Occupation;
import org.tctalent.anonymization.model.ResidenceStatus;
import org.tctalent.anonymization.model.SurveyType;
import org.tctalent.anonymization.model.UnhcrStatus;
import org.tctalent.anonymization.model.VaccinationStatus;
import org.tctalent.anonymization.model.WorkPermit;
import org.tctalent.anonymization.model.YesNo;
import org.tctalent.anonymization.model.YesNoUnsure;

@Getter
@Setter
@Document(collection = "candidates")
public class CandidateDocument extends AbstractDomainDocument<ObjectId> {

  private String publicId;

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
  private List<@Valid CandidateCertification> candidateCertifications = new ArrayList<>();

  @Valid
  private List<@Valid CandidateCitizenship> candidateCitizenships = new ArrayList<>();

  @Valid
  private List<@Valid Dependant> candidateDependants = new ArrayList<>();

  @Valid
  private List<@Valid Destination> candidateDestinations = new ArrayList<>();

  @Valid
  private List<@Valid CandidateEducation> candidateEducations = new ArrayList<>();

  @Valid
  private List<@Valid CandidateExam> candidateExams = new ArrayList<>();

  @Valid
  private List<@Valid CandidateJobExperience> candidateJobExperiences = new ArrayList<>();

  @Valid
  private List<@Valid CandidateLanguage> candidateLanguages = new ArrayList<>();

  private String candidateMessage;

  @Valid
  private List<@Valid CandidateNote> candidateNotes = new ArrayList<>();

  @Valid
  private List<@Valid CandidateOccupation> candidateOccupations = new ArrayList<>();

  @Valid
  private List<@Valid CandidateSkill> candidateSkills = new ArrayList<>();

  @Valid
  private List<@Valid CandidateVisaCheck> candidateVisaChecks = new ArrayList<>();

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
  private List<IntRecruitReason> intRecruitReasons = new ArrayList<>();

  private String intRecruitOther;
  private YesNoUnsure intRecruitRural;
  private String intRecruitRuralNotes;
  private String leftHomeNotes;

  @Valid
  private List<LeftHomeReason> leftHomeReasons = new ArrayList<>();

  private MaritalStatus maritalStatus;
  private String maritalStatusNotes;
  private EducationLevel maxEducationLevel;
  private String mediaWillingness;
  private EducationMajor migrationEducationMajor;
  private YesNo militaryService;
  private YesNoUnsure militaryWanted;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate militaryStart;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate militaryEnd;

  private String militaryNotes;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Instant miniIntakeCompletedDate;

  private YesNo monitoringEvaluationConsent;
  private Country nationality;
  private Long numberDependants;
  private YesNoUnsure partnerRegistered;
  private String partnerPublicId;

  @Valid
  private List<Long> partnerCitizenship = new ArrayList<>();

  private EducationLevel partnerEduLevel;
  private YesNo partnerEnglish;
  private LanguageLevel partnerEnglishLevel;
  private IeltsStatus partnerIelts;
  private String partnerIeltsScore;
  private Long partnerIeltsYr;
  private Occupation partnerOccupation;
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
  private String state;
  private CandidateStatus status;
  private SurveyType surveyType;
  private String surveyComment;
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
  private WorkPermit workPermit;
  private YesNoUnsure workPermitDesired;
  private String workPermitDesiredNotes;
  private Integer yearOfArrival;
  private Integer yearOfBirth;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Instant createdDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Instant updatedDate;
}

