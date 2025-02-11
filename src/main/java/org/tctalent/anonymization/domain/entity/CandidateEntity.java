package org.tctalent.anonymization.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.format.annotation.DateTimeFormat;
import org.tctalent.anonymization.domain.common.AvailImmediateReason;
import org.tctalent.anonymization.domain.common.CandidateStatus;
import org.tctalent.anonymization.domain.common.DocumentStatus;
import org.tctalent.anonymization.domain.common.Gender;
import org.tctalent.anonymization.domain.common.IeltsStatus;
import org.tctalent.anonymization.domain.common.IntRecruitReason;
import org.tctalent.anonymization.domain.common.LeftHomeReason;
import org.tctalent.anonymization.domain.common.MaritalStatus;
import org.tctalent.anonymization.domain.common.NotRegisteredStatus;
import org.tctalent.anonymization.domain.common.ResidenceStatus;
import org.tctalent.anonymization.domain.common.UnhcrStatus;
import org.tctalent.anonymization.domain.common.VaccinationStatus;
import org.tctalent.anonymization.domain.common.WorkPermit;
import org.tctalent.anonymization.domain.common.YesNo;
import org.tctalent.anonymization.domain.common.YesNoUnsure;
import org.tctalent.anonymization.domain.entity.converter.IntRecruitReasonConverter;
import org.tctalent.anonymization.domain.entity.converter.LeftHomeReasonsConverter;


@Getter
@Setter
@Entity
@Table(name = "candidate")
@SequenceGenerator(name = "seq_gen", sequenceName = "candidate_id_seq", allocationSize = 1)
@Slf4j
public class CandidateEntity extends AbstractDomainEntity<Long> {

  @Column(nullable = false, unique = true)
  private String publicId;

  private LocalDate asylumYear;

  @Enumerated(EnumType.STRING)
  private YesNoUnsure arrestImprison;

  private String arrestImprisonNotes;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate availDate;

  private YesNo availImmediate;

  private String availImmediateJobOps;

  @Enumerated(EnumType.STRING)
  private AvailImmediateReason availImmediateReason;

  private String availImmediateNotes;

  // Store the isoCode directly instead of a foreign key reference
  @Column(name = "birth_country_iso_code", nullable = true)
  private String birthCountryIsoCode;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("dateCompleted DESC")
  private List<CandidateCertification> candidateCertifications = new ArrayList<>();

  public void setCandidateCertifications(List<CandidateCertification> certifications) {
    this.candidateCertifications.clear();
    certifications.forEach(certification -> certification.setCandidate(this));
    this.candidateCertifications.addAll(certifications);
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CandidateCitizenship> candidateCitizenships = new ArrayList<>();

  public void setCandidateCitizenships(List<CandidateCitizenship> citizenships) {
    this.candidateCitizenships.clear();
    citizenships.forEach(citizenship -> citizenship.setCandidate(this));
    this.candidateCitizenships.addAll(citizenships);
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CandidateDependant> candidateDependants = new ArrayList<>();

  public void setCandidateDependants(List<CandidateDependant> dependants) {
    this.candidateDependants.clear();
    dependants.forEach(dependant -> dependant.setCandidate(this));
    this.candidateDependants.addAll(dependants);
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CandidateDestination> candidateDestinations = new ArrayList<>();

  public void setCandidateDestinations(List<CandidateDestination> destinations) {
    this.candidateDestinations.clear();
    destinations.forEach(destination -> destination.setCandidate(this));
    this.candidateDestinations.addAll(destinations);
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("yearCompleted DESC")
  private List<CandidateEducation> candidateEducations = new ArrayList<>();

  public void setCandidateEducations(List<CandidateEducation> educations) {
    this.candidateEducations.clear();
    educations.forEach(education -> education.setCandidate(this));
    this.candidateEducations.addAll(educations);
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<@Valid CandidateExam> candidateExams = new ArrayList<>();

  public void setCandidateExams(List<CandidateExam> exams) {
    this.candidateExams.clear();
    exams.forEach(exam -> exam.setCandidate(this));
    this.candidateExams.addAll(exams);
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CandidateLanguage> candidateLanguages = new ArrayList<>();

  public void setCandidateLanguages(List<CandidateLanguage> languages) {
    this.candidateLanguages.clear();
    languages.forEach(language -> language.setCandidate(this));
    this.candidateLanguages.addAll(languages);
  }

  private String candidateMessage;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CandidateOccupation> candidateOccupations = new ArrayList<>();

  public void setCandidateOccupations(List<CandidateOccupation> occupations) {
    this.candidateOccupations.clear();
    occupations.forEach(occupation -> occupation.setCandidate(this));
    this.candidateOccupations.addAll(occupations);
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CandidateSkill> candidateSkills = new ArrayList<>();

  public void setCandidateSkills(List<CandidateSkill> skills) {
    this.candidateSkills.clear();
    skills.forEach(skill -> skill.setCandidate(this));
    this.candidateSkills.addAll(skills);
  }

//  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
//  private List<CandidateVisaCheck> candidateVisaChecks = new ArrayList<>();
//
//  public void setCandidateVisaChecks(List<CandidateVisaCheck> visaChecks) {
//    this.candidateVisaChecks.clear();
//    visaChecks.forEach(visaCheck -> {
//      visaCheck.setCandidate(this);
//      visaCheck.getCandidateVisaJobChecks().forEach(jobCheck -> {
//        jobCheck.setCandidateVisaCheck(visaCheck);
//      });
//    });
//    this.candidateVisaChecks.addAll(visaChecks);
//  }
//
//  @Enumerated(EnumType.STRING)
//  private YesNo canDrive;
//
//  private String city;
//
//  @Enumerated(EnumType.STRING)
//  private YesNo conflict;
//
//  private String conflictNotes;
//
//  private Boolean contactConsentTcPartners;
//
//  private Boolean contactConsentRegistration;
//

  // Store the isoCode directly instead of a foreign key reference
  @Column(name = "country_iso_code", nullable = false)
  private String countryIsoCode;

  @Enumerated(EnumType.STRING)
  private YesNo covidVaccinated;

  private LocalDate covidVaccinatedDate;

  private String covidVaccineName;

  private String covidVaccineNotes;

  @Enumerated(EnumType.STRING)
  private VaccinationStatus covidVaccinatedStatus;

  @Enumerated(EnumType.STRING)
  private YesNoUnsure crimeConvict;

  private String crimeConvictNotes;

  @Enumerated(EnumType.STRING)
  private YesNo destLimit;

  private String destLimitNotes;

//  @Enumerated(EnumType.STRING)
//  private DocumentStatus drivingLicense;
//

  // Store the isoCode directly instead of a foreign key reference
  @Column(name = "driving_license_country_iso_code", nullable = true)
  private String drivingLicenseCountryIsoCode;

//
//  private LocalDate drivingLicenseExp;
//
//  private String englishAssessment;
//
//  private String englishAssessmentScoreIelts;
//
//  @Enumerated(EnumType.STRING)
//  private YesNo familyMove;
//
//  private String familyMoveNotes;
//
//  private String frenchAssessment;
//
//  private Long frenchAssessmentScoreNclc;
//
//  @Column(name = "full_intake_completed_date")
//  private OffsetDateTime fullIntakeCompletedDate;
//
//  @Enumerated(EnumType.STRING)
//  private Gender gender;
//
//  @Enumerated(EnumType.STRING)
//  private YesNo healthIssues;
//
//  private String healthIssuesNotes;
//
//  private String homeLocation;
//
//  private String hostChallenges;
//
//  @Enumerated(EnumType.STRING)
//  private YesNo hostEntryLegally;
//
//  private String hostEntryLegallyNotes;
//
//  private Integer hostEntryYear;
//
//  private String hostEntryYearNotes;
//
//  private BigDecimal ieltsScore;
//
//  @Convert(converter = IntRecruitReasonConverter.class)
//  private List<IntRecruitReason> intRecruitReasons;
//
//  private String intRecruitOther;
//
//  @Enumerated(EnumType.STRING)
//  private YesNoUnsure intRecruitRural;
//
//  private String intRecruitRuralNotes;
//
//  private String leftHomeNotes;
//
//  @Convert(converter = LeftHomeReasonsConverter.class)
//  private List<LeftHomeReason> leftHomeReasons;
//
//  @Enumerated(EnumType.STRING)
//  private MaritalStatus maritalStatus;
//
//  private String maritalStatusNotes;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "max_education_level_id")
//  private EducationLevel maxEducationLevel;
//
//  private String mediaWillingness;
//
//  @OneToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "migration_education_major_id")
//  private EducationMajor migrationEducationMajor;
//
//  @Enumerated(EnumType.STRING)
//  private YesNo militaryService;
//
//  @Enumerated(EnumType.STRING)
//  private YesNoUnsure militaryWanted;
//
//  private LocalDate militaryStart;
//
//  private LocalDate militaryEnd;
//
//  private String militaryNotes;
//
//  @Column(name = "mini_intake_completed_date")
//  private OffsetDateTime miniIntakeCompletedDate;
//
//  @Enumerated(EnumType.STRING)
//  private YesNo monitoringEvaluationConsent;
//

  // Store the isoCode directly instead of a foreign key reference
  @Column(name = "nationality_iso_code", nullable = false)
  private String nationalityIsoCode;


//  private Long numberDependants;
//
//  @Enumerated(EnumType.STRING)
//  private YesNoUnsure partnerRegistered;
//
//  private String partnerPublicId;
//
////  @Valid
////  // todo - sm - this is actually stored as a CSV string and will need to be mapped from the rest model
////  private List<Long> partnerCitizenship = new ArrayList<>();
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "partner_edu_level_id")
//  private EducationLevel partnerEduLevel;
//
//  @Enumerated(EnumType.STRING)
//  private YesNo partnerEnglish;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "partner_english_level_id")
//  private LanguageLevel partnerEnglishLevel;
//
//  @Enumerated(EnumType.STRING)
//  private IeltsStatus partnerIelts;
//
//  private String partnerIeltsScore;
//
//  private Long partnerIeltsYr;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "partner_occupation_id")
//  private Occupation partnerOccupation;
//
//  @Enumerated(EnumType.STRING)
//  private ResidenceStatus residenceStatus;
//
//  private String residenceStatusNotes;
//
//  @Enumerated(EnumType.STRING)
//  private YesNoUnsure returnedHome;
//
//  private String returnedHomeReason;
//
//  private String returnedHomeReasonNo;
//
//  @Enumerated(EnumType.STRING)
//  private YesNoUnsure returnHomeSafe;
//
//  @Enumerated(EnumType.STRING)
//  private YesNoUnsure returnHomeFuture;
//
//  private LocalDate returnHomeWhen; // todo - sm - check this type because it is a String type in the TC entity definition
//
//  @Enumerated(EnumType.STRING)
//  private YesNo resettleThird;
//
//  private String resettleThirdStatus;
//
//  private String state;
//
//  @Enumerated(EnumType.STRING)
//  private CandidateStatus status;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "survey_type_id")
//  private SurveyType surveyType;
//
//  private String surveyComment;
//
//  private YesNo unhcrConsent;
//
//  private String unhcrNotes;
//
//  @Enumerated(EnumType.STRING)
//  private NotRegisteredStatus unhcrNotRegStatus;
//
//  @Enumerated(EnumType.STRING)
//  private YesNoUnsure unhcrRegistered;
//
//  @Enumerated(EnumType.STRING)
//  private UnhcrStatus unhcrStatus;
//
//  private String unrwaNotes;
//
//  @Enumerated(EnumType.STRING)
//  private NotRegisteredStatus unrwaNotRegStatus;
//
//  @Enumerated(EnumType.STRING)
//  private YesNoUnsure unrwaRegistered;
//
//  @Enumerated(EnumType.STRING)
//  private YesNoUnsure visaIssues;
//
//  private String visaIssuesNotes;
//
//  @Enumerated(EnumType.STRING)
//  private YesNoUnsure visaReject;
//
//  private String visaRejectNotes;
//
//  @Enumerated(EnumType.STRING)
//  private YesNo workAbroad;
//
//  private String workAbroadNotes;
//
//  @Enumerated(EnumType.STRING)
//  private WorkPermit workPermit;
//
//  @Enumerated(EnumType.STRING)
//  private YesNoUnsure workPermitDesired;
//
//  private String workPermitDesiredNotes;
//
//  private Integer yearOfArrival;
//
//  private Integer yearOfBirth;

  @Override
  public String toString() {
    return this.getClass().getName();
  }

}
