package org.tctalent.anonymization.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.tctalent.anonymization.domain.common.AvailImmediateReason;
import org.tctalent.anonymization.domain.common.CandidateStatus;
import org.tctalent.anonymization.domain.common.DocumentStatus;
import org.tctalent.anonymization.domain.common.Gender;
import org.tctalent.anonymization.domain.common.HowHeardAboutUs;
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

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate availDate;

  private YesNo availImmediate;

  @Size(max = 255)
  @Column(name = "avail_immediate_job_ops", length = 255)
  private String availImmediateJobOps;

  @Enumerated(EnumType.STRING)
  private AvailImmediateReason availImmediateReason;

  // Store the isoCode directly instead of a foreign key reference
  @Size(max = 3)
  @Column(name = "birth_country_iso_code", nullable = true, length = 3)
  private String birthCountryIsoCode;

  /**
   * One-to-many relationship with CandidateCertification.
   * CascadeType.ALL cascades all operations (persist, merge, remove, etc.) to certifications.
   * orphanRemoval=true ensures that certifications removed from this list are deleted.
   */
  @Valid
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("dateCompleted DESC")
  private List<CandidateCertification> candidateCertifications = new ArrayList<>();

  /**
   * Replaces the candidate's certifications.
   * Clears the existing list (triggering orphan removal) and sets the candidate reference on each certification
   * to maintain bidirectional consistency.
   *
   * @param certifications the new list of certifications
   */
  public void setCandidateCertifications(List<CandidateCertification> certifications) {
    this.candidateCertifications.clear();
    certifications.forEach(certification -> certification.setCandidate(this));
    this.candidateCertifications.addAll(certifications);
  }

  @Valid
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CandidateCitizenship> candidateCitizenships = new ArrayList<>();

  public void setCandidateCitizenships(List<CandidateCitizenship> citizenships) {
    this.candidateCitizenships.clear();
    citizenships.forEach(citizenship -> citizenship.setCandidate(this));
    this.candidateCitizenships.addAll(citizenships);
  }

  @Valid
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CandidateDependant> candidateDependants = new ArrayList<>();

  public void setCandidateDependants(List<CandidateDependant> dependants) {
    this.candidateDependants.clear();
    dependants.forEach(dependant -> dependant.setCandidate(this));
    this.candidateDependants.addAll(dependants);
  }

  @Valid
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CandidateDestination> candidateDestinations = new ArrayList<>();

  public void setCandidateDestinations(List<CandidateDestination> destinations) {
    this.candidateDestinations.clear();
    destinations.forEach(destination -> destination.setCandidate(this));
    this.candidateDestinations.addAll(destinations);
  }

  @Valid
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("yearCompleted DESC")
  private List<CandidateEducation> candidateEducations = new ArrayList<>();

  public void setCandidateEducations(List<CandidateEducation> educations) {
    this.candidateEducations.clear();
    educations.forEach(education -> education.setCandidate(this));
    this.candidateEducations.addAll(educations);
  }

  @Valid
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<@Valid CandidateExam> candidateExams = new ArrayList<>();

  public void setCandidateExams(List<CandidateExam> exams) {
    this.candidateExams.clear();
    exams.forEach(exam -> exam.setCandidate(this));
    this.candidateExams.addAll(exams);
  }

  @Valid
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CandidateLanguage> candidateLanguages = new ArrayList<>();

  public void setCandidateLanguages(List<CandidateLanguage> languages) {
    this.candidateLanguages.clear();
    languages.forEach(language -> language.setCandidate(this));
    this.candidateLanguages.addAll(languages);
  }

  @Valid
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CandidateOccupation> candidateOccupations = new ArrayList<>();

  public void setCandidateOccupations(List<CandidateOccupation> occupations) {
    this.candidateOccupations.clear();
    occupations.forEach(occupation -> occupation.setCandidate(this));
    this.candidateOccupations.addAll(occupations);
  }

  @Valid
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CandidateSkill> candidateSkills = new ArrayList<>();

  public void setCandidateSkills(List<CandidateSkill> skills) {
    this.candidateSkills.clear();
    skills.forEach(skill -> skill.setCandidate(this));
    this.candidateSkills.addAll(skills);
  }

  @Valid
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CandidateVisaCheck> candidateVisaChecks = new ArrayList<>();

  public void setCandidateVisaChecks(List<CandidateVisaCheck> visaChecks) {
    this.candidateVisaChecks.clear();
    visaChecks.forEach(visaCheck -> visaCheck.setCandidate(this));
    this.candidateVisaChecks.addAll(visaChecks);
  }

  @Enumerated(EnumType.STRING)
  private YesNo canDrive;

  @Size(max = 255)
  @Column(name = "city", length = 255)
  private String city;

  @Enumerated(EnumType.STRING)
  private YesNo conflict;

  private Boolean contactConsentRegistration;

  private Boolean contactConsentTcPartners;

  // Store the isoCode directly instead of a foreign key reference
  @Size(max = 3)
  @Column(name = "country_iso_code", nullable = false, length = 3)
  private String countryIsoCode;

  @Enumerated(EnumType.STRING)
  private YesNo covidVaccinated;

  private LocalDate covidVaccinatedDate;

  @Size(max = 255)
  @Column(name = "covid_vaccine_name", length = 255)
  private String covidVaccineName;

  @Enumerated(EnumType.STRING)
  private VaccinationStatus covidVaccinatedStatus;

  @Enumerated(EnumType.STRING)
  private YesNoUnsure crimeConvict;

  @Enumerated(EnumType.STRING)
  private YesNo destLimit;

  @Enumerated(EnumType.STRING)
  private DocumentStatus drivingLicense;

  // Store the isoCode directly instead of a foreign key reference
  @Size(max = 3)
  @Column(name = "driving_license_country_iso_code", nullable = true, length = 3)
  private String drivingLicenseCountryIsoCode;

  private LocalDate drivingLicenseExp;

  @Size(max = 255)
  @Column(name = "english_assessment_score_ielts", length = 255)
  private String englishAssessmentScoreIelts;

  @Enumerated(EnumType.STRING)
  private YesNo familyMove;

  private Long frenchAssessmentScoreNclc;

  @Column(name = "full_intake_completed_date")
  private OffsetDateTime fullIntakeCompletedDate;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Enumerated(EnumType.STRING)
  private YesNo healthIssues;

  @Enumerated(EnumType.STRING)
  private YesNo hostEntryLegally;

  private Integer hostEntryYear;

  private BigDecimal ieltsScore;

  @Convert(converter = IntRecruitReasonConverter.class)
  @Size(max = 255)
  @Column(name = "int_recruit_reasons", length = 255)
  private List<IntRecruitReason> intRecruitReasons;

  @Enumerated(EnumType.STRING)
  private YesNoUnsure intRecruitRural;

  @Convert(converter = LeftHomeReasonsConverter.class)
  @Size(max = 255)
  @Column(name = "left_home_reasons", length = 255)
  private List<LeftHomeReason> leftHomeReasons;

  @Enumerated(EnumType.STRING)
  private MaritalStatus maritalStatus;

  // Store the education level directly instead of a foreign key reference
  @Column(name = "max_education_level", nullable = true)
  private Integer maxEducationLevel;

  @Size(max = 255)
  @Column(name = "media_willingness", length = 255)
  private String mediaWillingness;

  @Enumerated(EnumType.STRING)
  private YesNo militaryService;

  @Enumerated(EnumType.STRING)
  private YesNoUnsure militaryWanted;

  private LocalDate militaryStart;

  private LocalDate militaryEnd;

  @Column(name = "mini_intake_completed_date")
  private OffsetDateTime miniIntakeCompletedDate;

  @Enumerated(EnumType.STRING)
  private YesNo monitoringEvaluationConsent;

  // Store the isoCode directly instead of a foreign key reference
  @Size(max = 3)
  @Column(name = "nationality_iso_code", nullable = false, length = 3)
  private String nationalityIsoCode;

  private Long numberDependants;

  @Enumerated(EnumType.STRING)
  private YesNoUnsure partnerRegistered;

  @Size(max = 255)
  @Column(name = "partner_public_id", length = 255)
  private String partnerPublicId;

  @Size(max = 255)
  @Column(name = "partner_citizenship", length = 255)
  private String partnerCitizenship;

  /**
   * Provides a list of country IDs for the candidate's partner's citizenships,
   * instead of the comma separated string we store on the DB.
   * Not currently used but left in case of future utility.
   * @return list of country IDs or null if nothing stored
   */
  @Nullable
  public List<Long> getPartnerCitizenship() {
    return partnerCitizenship != null ?
        Stream.of(partnerCitizenship.split(","))
            .map(Long::parseLong)
            .collect(Collectors.toList()) : null;
  }

  /**
   * Converts an array of country IDs to a comma-separated string for DB storage.
   * @param partnerCitizenshipCountryIds array of country IDs indicating countries of which a
   *                                     given candidate's partner is a citizen.
   */
  public void setPartnerCitizenship(List<Long> partnerCitizenshipCountryIds) {
    this.partnerCitizenship = !CollectionUtils.isEmpty(partnerCitizenshipCountryIds) ?
        partnerCitizenshipCountryIds.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(",")) : null;
  }

  // Store the education level directly instead of a foreign key reference
  @Column(name = "partner_edu_level", nullable = true)
  private Integer partnerEduLevel;

  @Enumerated(EnumType.STRING)
  private YesNo partnerEnglish;

  // Store the written_level directly instead of a foreign key reference
  @Size(max = 255)
  @Column(name = "partner_english_level", nullable = true, length = 255)
  private String partnerEnglishLevel;

  @Enumerated(EnumType.STRING)
  private IeltsStatus partnerIelts;

  @Size(max = 255)
  @Column(name = "partner_ielts_score", length = 255)
  private String partnerIeltsScore;

  private Long partnerIeltsYr;

  // Store the isco08Code directly instead of a foreign key reference
  @Size(max = 255)
  @Column(name = "partner_occupation_isco08_code", nullable = true)
  private String partnerOccupationIsco08Code;

  // NB: When isco08Codes static has been updated, this field will become redundant and can be removed
  @Column(name = "partner_occupation_name", nullable = true)
  private String partnerOccupationName;

  @Enumerated(EnumType.STRING)
  private ResidenceStatus residenceStatus;

  @Enumerated(EnumType.STRING)
  private YesNoUnsure returnedHome;

  @Enumerated(EnumType.STRING)
  private YesNoUnsure returnHomeSafe;

  @Enumerated(EnumType.STRING)
  private YesNoUnsure returnHomeFuture;

  @Enumerated(EnumType.STRING)
  private YesNo resettleThird;


  @Enumerated(EnumType.STRING)
  private CandidateStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "how_heard_about_us", nullable = true)
  private HowHeardAboutUs howHeardAboutUs;

  private YesNo unhcrConsent;

  @Enumerated(EnumType.STRING)
  private NotRegisteredStatus unhcrNotRegStatus;

  @Enumerated(EnumType.STRING)
  private YesNoUnsure unhcrRegistered;

  @Enumerated(EnumType.STRING)
  private UnhcrStatus unhcrStatus;

  @Enumerated(EnumType.STRING)
  private NotRegisteredStatus unrwaNotRegStatus;

  @Enumerated(EnumType.STRING)
  private YesNoUnsure unrwaRegistered;

  @Enumerated(EnumType.STRING)
  private YesNoUnsure visaIssues;

  @Enumerated(EnumType.STRING)
  private YesNoUnsure visaReject;

  @Enumerated(EnumType.STRING)
  private YesNo workAbroad;

  @Enumerated(EnumType.STRING)
  private WorkPermit workPermit;

  @Enumerated(EnumType.STRING)
  private YesNoUnsure workPermitDesired;

  private Integer yearOfArrival;

  private Integer yearOfBirth;

  @Column(name = "created_date")
  private OffsetDateTime createdDate;

}
