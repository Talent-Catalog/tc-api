package org.tctalent.anonymization.bootstrap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.domain.common.AvailImmediateReason;
import org.tctalent.anonymization.domain.common.CandidateStatus;
import org.tctalent.anonymization.domain.common.DependantRelations;
import org.tctalent.anonymization.domain.common.DocumentStatus;
import org.tctalent.anonymization.domain.common.EducationType;
import org.tctalent.anonymization.domain.common.Exam;
import org.tctalent.anonymization.domain.common.FamilyRelations;
import org.tctalent.anonymization.domain.common.Gender;
import org.tctalent.anonymization.domain.common.HasPassport;
import org.tctalent.anonymization.domain.common.IeltsStatus;
import org.tctalent.anonymization.domain.common.IntRecruitReason;
import org.tctalent.anonymization.domain.common.JobOpportunityStage;
import org.tctalent.anonymization.domain.common.LeftHomeReason;
import org.tctalent.anonymization.domain.common.MaritalStatus;
import org.tctalent.anonymization.domain.common.NotRegisteredStatus;
import org.tctalent.anonymization.domain.common.OtherVisas;
import org.tctalent.anonymization.domain.common.Registration;
import org.tctalent.anonymization.domain.common.ResidenceStatus;
import org.tctalent.anonymization.domain.common.RiskLevel;
import org.tctalent.anonymization.domain.common.Status;
import org.tctalent.anonymization.domain.common.TcEligibilityAssessment;
import org.tctalent.anonymization.domain.common.UnhcrStatus;
import org.tctalent.anonymization.domain.common.VaccinationStatus;
import org.tctalent.anonymization.domain.common.VisaEligibility;
import org.tctalent.anonymization.domain.common.WorkPermit;
import org.tctalent.anonymization.domain.common.YesNo;
import org.tctalent.anonymization.domain.common.YesNoUnsure;
import org.tctalent.anonymization.domain.entity.CandidateCertification;
import org.tctalent.anonymization.domain.entity.CandidateCitizenship;
import org.tctalent.anonymization.domain.entity.CandidateDependant;
import org.tctalent.anonymization.domain.entity.CandidateDestination;
import org.tctalent.anonymization.domain.entity.CandidateEducation;
import org.tctalent.anonymization.domain.entity.CandidateEntity;
import org.tctalent.anonymization.domain.entity.CandidateExam;
import org.tctalent.anonymization.domain.entity.CandidateJobExperience;
import org.tctalent.anonymization.domain.entity.CandidateLanguage;
import org.tctalent.anonymization.domain.entity.CandidateOccupation;
import org.tctalent.anonymization.domain.entity.CandidateSkill;
import org.tctalent.anonymization.domain.entity.CandidateVisaCheck;
import org.tctalent.anonymization.domain.entity.CandidateVisaJobCheck;
import org.tctalent.anonymization.domain.entity.Country;
import org.tctalent.anonymization.domain.entity.EducationLevel;
import org.tctalent.anonymization.domain.entity.EducationMajor;
import org.tctalent.anonymization.domain.entity.Employer;
import org.tctalent.anonymization.domain.entity.Language;
import org.tctalent.anonymization.domain.entity.LanguageLevel;
import org.tctalent.anonymization.domain.entity.Occupation;
import org.tctalent.anonymization.domain.entity.SalesforceJobOpp;
import org.tctalent.anonymization.domain.entity.SurveyType;
import org.tctalent.anonymization.repository.CandidateEntityRepository;

@Component
@Profile("bootstrap") // Runs only when the bootstrap profile is active
@RequiredArgsConstructor
public class BootstrapCandidate implements CommandLineRunner {

  private final CandidateEntityRepository candidateRepository;

    @Override
    public void run(String... args) throws Exception {
      candidateRepository.deleteAll();

      if (candidateRepository.count() == 0) {
          loadCandidates();
      }
    }

    private void loadCandidates() {
      // Create a CandidateEntity
      CandidateEntity candidate = new CandidateEntity();
      candidate.setPublicId("test-public-id-123");
      candidate.setAsylumYear(LocalDate.of(2010, 1, 1));
      candidate.setArrestImprison(YesNoUnsure.NO);
      candidate.setAvailDate(LocalDate.of(2025, 1, 1));
      candidate.setAvailImmediate(YesNo.YES);
      candidate.setAvailImmediateJobOps("Available immediately");
      candidate.setAvailImmediateReason(AvailImmediateReason.OTHER);
      candidate.setBirthCountryIsoCode("US");
      candidate.setCandidateCertifications(createCertifications());
      candidate.setCandidateCitizenships(createCandidateCitizenships());
      candidate.setCandidateDependants(createDependants());
      candidate.setCandidateDestinations(createDestinations());
      candidate.setCandidateEducations(createEducations());
      candidate.setCandidateExams(createExams());
      candidate.setCandidateLanguages(List.of(
          createCandidateLanguage(342L, "en", "English"),
          createCandidateLanguage(346L, "es", "Spanish")));
      candidate.setCandidateOccupations(List.of(
          createCandidateOccupation(8577L, "2411", "Accountant", Status.ACTIVE),
          createCandidateOccupation(8484L, "3343", "Administrative assistant", Status.ACTIVE)));
      candidate.setCandidateSkills(List.of(
          createCandidateSkill("Skill 1", "1 year"),
          createCandidateSkill("Skill 2", "2 years")));
      candidate.setCandidateVisaChecks(candidateVisaChecks());
      candidate.setCanDrive(YesNo.YES);
      candidate.setCity("City 1");
      candidate.setConflict(YesNo.NO);
      candidate.setContactConsentTcPartners(true);
      candidate.setContactConsentRegistration(true);
      candidate.setCountryIsoCode("AF");
      candidate.setCovidVaccinated(YesNo.YES);
      candidate.setCovidVaccinatedDate(LocalDate.of(2021, 1, 1));
      candidate.setCovidVaccineName("Vaccine 1");
      candidate.setCovidVaccinatedStatus(VaccinationStatus.FULL);
      candidate.setCrimeConvict(YesNoUnsure.NO);
      candidate.setDestLimit(YesNo.NO);
      candidate.setDrivingLicenseCountryIsoCode("US");
      candidate.setDrivingLicenseExp(LocalDate.of(2025, 1, 1));
      candidate.setEnglishAssessmentScoreIelts("7.5");
      candidate.setFamilyMove(YesNo.YES);
      candidate.setFrenchAssessmentScoreNclc(82L);
      candidate.setFullIntakeCompletedDate(OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC));
      candidate.setGender(Gender.MALE);
      candidate.setHealthIssues(YesNo.NO);
      candidate.setHostEntryLegally(YesNo.YES);
      candidate.setIeltsScore(new BigDecimal("7.5"));
      candidate.setIntRecruitReasons(List.of(IntRecruitReason.EXPERIENCE, IntRecruitReason.CITIZENSHIP));
      candidate.setIntRecruitRural(YesNoUnsure.YES);
      candidate.setLeftHomeReasons(List.of(LeftHomeReason.SAFETY, LeftHomeReason.JOB));
      candidate.setMaritalStatus(MaritalStatus.MARRIED);
      candidate.setMaxEducationLevel(100);
      candidate.setMediaWillingness("Media willingness");
      candidate.setMilitaryService(YesNo.YES);
      candidate.setMilitaryWanted(YesNoUnsure.YES);
      candidate.setMilitaryStart(LocalDate.of(2005, 1, 1));
      candidate.setMilitaryEnd(LocalDate.of(2010, 1, 1));
      candidate.setMiniIntakeCompletedDate(OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC));
      candidate.setMonitoringEvaluationConsent(YesNo.YES);
      candidate.setNationalityIsoCode("US");
      candidate.setNumberDependants(2L);
      candidate.setPartnerRegistered(YesNoUnsure.YES);
      candidate.setPartnerPublicId("partner-public-id-123");
      candidate.setPartnerEduLevel(100);
      candidate.setPartnerEnglish(YesNo.YES);
      candidate.setPartnerEnglishLevel("Full Professional Proficiency");
      candidate.setPartnerIelts(IeltsStatus.YES_ACADEMIC);
      candidate.setPartnerIeltsScore("7.5");
      candidate.setPartnerIeltsYr(2010L);
      candidate.setPartnerOccupationIsco08Code("2411");
      candidate.setPartnerOccupationName("Accountant");
      candidate.setResidenceStatus(ResidenceStatus.LEGAL_RES);
      candidate.setReturnedHome(YesNoUnsure.YES);
      candidate.setReturnHomeSafe(YesNoUnsure.NO_RESPONSE);
      candidate.setReturnHomeFuture(YesNoUnsure.NO_RESPONSE);
      candidate.setResettleThird(YesNo.NO_RESPONSE);
      candidate.setStatus(CandidateStatus.ACTIVE);
      candidate.setSurveyType("Information Session");
      candidate.setUnhcrConsent(YesNo.YES);
      candidate.setUnhcrNotRegStatus(NotRegisteredStatus.NA);
      candidate.setUnhcrRegistered(YesNoUnsure.UNSURE);
      candidate.setUnhcrStatus(UnhcrStatus.NO_RESPONSE);
      candidate.setUnrwaRegistered(YesNoUnsure.YES);
      candidate.setVisaIssues(YesNoUnsure.NO);
      candidate.setVisaReject(YesNoUnsure.NO);
      candidate.setWorkAbroad(YesNo.YES);
      candidate.setWorkPermit(WorkPermit.NO);
      candidate.setWorkPermitDesired(YesNoUnsure.YES);
      candidate.setYearOfArrival(2010);
      candidate.setYearOfBirth(1990);

      // Save to database
      CandidateEntity savedCandidate = candidateRepository.save(candidate);
      System.out.println("Bootstrap candidate saved with id: " + savedCandidate.getId());

    }

    private Country createCountry(Long id, String name, String isoCode, Status status) {
      Country country = new Country();
      country.setId(id);
      country.setName(name);
      country.setIsoCode(isoCode);
      country.setStatus(status);
      return country;
    }

    private List<CandidateCertification> createCertifications() {
      CandidateCertification certification1 = new CandidateCertification();
      certification1.setName("Certification 1");
      certification1.setInstitution("Institution 1");
      certification1.setDateCompleted(LocalDate.of(2010, 1, 1));

      CandidateCertification certification2 = new CandidateCertification();
      certification2.setName("Certification 2");
      certification2.setInstitution("Institution 2");
      certification2.setDateCompleted(LocalDate.now());

      return List.of(certification1, certification2);
    }

    private List<CandidateCitizenship> createCandidateCitizenships() {
      CandidateCitizenship citizenship1 = new CandidateCitizenship();
      citizenship1.setHasPassport(HasPassport.VALID_PASSPORT);
      citizenship1.setPassportExp(LocalDate.of(2025, 1, 1));
      citizenship1.setNationalityIsoCode("AF");

      CandidateCitizenship citizenship2 = new CandidateCitizenship();
      citizenship2.setHasPassport(HasPassport.INVALID_PASSPORT);
      citizenship2.setNationalityIsoCode("US");

      return List.of(citizenship1, citizenship2);
    }

    private List<CandidateDependant> createDependants() {
      CandidateDependant dependant1 = new CandidateDependant();
      dependant1.setRelation(DependantRelations.CHILD);
      dependant1.setRelationOther("Adopted");
      dependant1.setYearOfBirth(2010);
      dependant1.setGender(Gender.FEMALE);
      dependant1.setRegistered(Registration.UNHCR);
      dependant1.setHealthConcern(YesNo.YES);

      CandidateDependant dependant2 = new CandidateDependant();
      dependant2.setRelation(DependantRelations.PARTNER);
      dependant2.setRelationOther("Spouse");
      dependant2.setYearOfBirth(1985);
      dependant2.setGender(Gender.MALE);
      dependant2.setRegistered(Registration.UNRWA);
      dependant2.setHealthConcern(YesNo.NO);

      return List.of(dependant1, dependant2);
    }

    private List<CandidateDestination> createDestinations() {
      CandidateDestination destination1 = new CandidateDestination();
      destination1.setCountryIsoCode( "AF");
      destination1.setInterest(YesNoUnsure.YES);

      CandidateDestination destination2 = new CandidateDestination();
      destination2.setCountryIsoCode( "US");
      destination2.setInterest(YesNoUnsure.NO);

      return List.of(destination1, destination2);
    }

    private List<CandidateEducation> createEducations() {
      CandidateEducation education1 = new CandidateEducation();
      education1.setEducationType(EducationType.ASSOCIATE);
      education1.setCountryIsoCode("AF");
      education1.setEducationMajor(createEducationMajor(8713L, "0111", "Major 1", Status.ACTIVE));
      education1.setLengthOfCourseYears(2);
      education1.setInstitution("Institution 1");
      education1.setCourseName("Course 1");
      education1.setYearCompleted(2010);
      education1.setIncomplete(false);

      CandidateEducation education2 = new CandidateEducation();
      education2.setEducationType(EducationType.BACHELOR);
      education2.setCountryIsoCode("US");
      education2.setEducationMajor(createEducationMajor(8714L, "0112", "Major 2", Status.ACTIVE));
      education2.setLengthOfCourseYears(4);
      education2.setInstitution("Institution 2");
      education2.setCourseName("Course 2");
      education2.setYearCompleted(2014);
      education2.setIncomplete(false);

      return List.of(education1, education2);
    }

    private EducationMajor createEducationMajor(Long id, String iscedCode, String name, Status status) {
      EducationMajor major = new EducationMajor();
      major.setId(id);
      major.setIscedCode(iscedCode);
      major.setName(name);
      major.setStatus(status);
      return major;
    }

    private List<CandidateExam> createExams() {
      CandidateExam exam1 = new CandidateExam();
      exam1.setExam(Exam.IELTS_GEN);
      exam1.setOtherExam("Other exam 1");
      exam1.setScore("7.5");
      exam1.setYear(2010L);

      CandidateExam exam2 = new CandidateExam();
      exam2.setExam(Exam.TOEFL);
      exam2.setOtherExam("Other exam 2");
      exam2.setScore("100");
      exam2.setYear(2014L);

      return List.of(exam1, exam2);
    }

    private List<CandidateJobExperience> createJobExperiences() {
      CandidateJobExperience jobExperience1 = new CandidateJobExperience();
      jobExperience1.setCountryIsoCode("AF");
      jobExperience1.setCompanyName("Company 1");
      jobExperience1.setRole("Role 1");
      jobExperience1.setStartDate(LocalDate.of(2010, 1, 1));
      jobExperience1.setEndDate(LocalDate.of(2014, 1, 1));
      jobExperience1.setFullTime(true);
      jobExperience1.setPaid(true);
      jobExperience1.setDescription("Description 1");

      CandidateJobExperience jobExperience2 = new CandidateJobExperience();
      jobExperience2.setCountryIsoCode("US");
      jobExperience2.setCompanyName("Company 2");
      jobExperience2.setRole("Role 2");
      jobExperience2.setStartDate(LocalDate.of(2014, 1, 1));
      jobExperience2.setEndDate(LocalDate.of(2018, 1, 1));
      jobExperience2.setFullTime(true);
      jobExperience2.setPaid(true);
      jobExperience2.setDescription("Description 2");

      return List.of(jobExperience1, jobExperience2);
    }

  private CandidateOccupation createCandidateOccupation(long id, String number, String s, Status status) {
      CandidateOccupation candidateOccupation = new CandidateOccupation();
      candidateOccupation.setIsco08Code(number);
      candidateOccupation.setYearsExperience(5L);
      return candidateOccupation;
  }

  private Occupation createOccupation(long id, String isoCode, String name) {
      Occupation occupation = new Occupation();
      occupation.setId(id);
      occupation.setIsco08Code(isoCode);
      occupation.setName(name);
      return occupation;
  }

  private CandidateLanguage createCandidateLanguage(long id, String isoCode, String name) {
      CandidateLanguage candidateLanguage = new CandidateLanguage();
      candidateLanguage.setName(name);
      candidateLanguage.setWrittenLevelName("Full Professional Proficiency");
      candidateLanguage.setSpokenLevelName("Full Professional Proficiency");
      return candidateLanguage;
  }

  private Language createLanguage(long id, String isoCode, String name) {
      Language language = new Language();
      language.setId(id);
      language.setIsoCode(isoCode);
      language.setName(name);
      language.setStatus(Status.ACTIVE);
      return language;
  }

  private LanguageLevel createLanguageLevel() {
      LanguageLevel languageLevel = new LanguageLevel();
      languageLevel.setId(351L);
      languageLevel.setLevel(30);
      languageLevel.setName("Full Professional Proficiency");
      languageLevel.setStatus(Status.ACTIVE);
      return languageLevel;
  }

  private CandidateSkill createCandidateSkill(String skill, String timePeriod) {
    CandidateSkill candidateSkill = new CandidateSkill();
    candidateSkill.setSkill(skill);
    candidateSkill.setTimePeriod(timePeriod);
    return candidateSkill;
  }

  private List<CandidateVisaCheck> candidateVisaChecks() {
      CandidateVisaCheck visaCheck1 = new CandidateVisaCheck();
      visaCheck1.setCountryIsoCode("AF");
      visaCheck1.setProtection(YesNo.YES);
      visaCheck1.setEnglishThreshold(YesNo.YES);
      visaCheck1.setHealthAssessment(YesNo.YES);
      visaCheck1.setCharacterAssessment(YesNo.YES);
      visaCheck1.setSecurityRisk(YesNo.YES);
      visaCheck1.setOverallRisk(RiskLevel.HIGH);
      visaCheck1.setValidTravelDocs(DocumentStatus.VALID);
      visaCheck1.setPathwayAssessment(YesNoUnsure.YES);
      visaCheck1.setDestinationFamily(FamilyRelations.AUNT_UNCLE);
      visaCheck1.setCandidateVisaJobChecks(createCandidateVisaJobChecks());

      CandidateVisaCheck visaCheck2 = new CandidateVisaCheck();
      visaCheck2.setCountryIsoCode("US");
      visaCheck2.setProtection(YesNo.NO);
      visaCheck2.setEnglishThreshold(YesNo.NO);
      visaCheck2.setHealthAssessment(YesNo.NO);
      visaCheck2.setCharacterAssessment(YesNo.NO);
      visaCheck2.setSecurityRisk(YesNo.NO);
      visaCheck2.setOverallRisk(RiskLevel.LOW);
      visaCheck2.setValidTravelDocs(DocumentStatus.EXPIRED);
      visaCheck2.setPathwayAssessment(YesNoUnsure.NO);
      visaCheck2.setDestinationFamily(FamilyRelations.COUSIN);
      visaCheck2.setCandidateVisaJobChecks(createCandidateVisaJobChecks());

      return List.of(visaCheck1, visaCheck2);
  }

  private Set<CandidateVisaJobCheck> createCandidateVisaJobChecks() {
      CandidateVisaJobCheck visaJobCheck1 = new CandidateVisaJobCheck();
      visaJobCheck1.setJobOpp(createJobOpp());
      visaJobCheck1.setInterest(YesNo.YES);
      visaJobCheck1.setQualification(YesNo.YES);
      visaJobCheck1.setIsco08Code("2411");
      visaJobCheck1.setSalaryTsmit(YesNo.YES);
      visaJobCheck1.setRegional(YesNo.YES);
      visaJobCheck1.setEligible_494(YesNo.YES);
      visaJobCheck1.setEligible_186(YesNo.YES);
      visaJobCheck1.setEligibleOther(OtherVisas.OTHER_HUM);
      visaJobCheck1.setPutForward(VisaEligibility.YES);
      visaJobCheck1.setTcEligibility(TcEligibilityAssessment.PROCEED);
      visaJobCheck1.setAgeRequirement("18-45");
      visaJobCheck1.setLanguagesRequired(List.of(342L, 346L));
      visaJobCheck1.setLanguagesThresholdMet(YesNo.YES);

      CandidateVisaJobCheck visaJobCheck2 = new CandidateVisaJobCheck();
      visaJobCheck2.setJobOpp(createJobOpp());
      visaJobCheck2.setInterest(YesNo.NO);
      visaJobCheck2.setQualification(YesNo.NO);
      visaJobCheck2.setIsco08Code("3343");
      visaJobCheck2.setSalaryTsmit(YesNo.NO);
      visaJobCheck2.setRegional(YesNo.NO);
      visaJobCheck2.setEligible_494(YesNo.NO);
      visaJobCheck2.setEligible_186(YesNo.NO);
      visaJobCheck2.setEligibleOther(OtherVisas.OTHER_HUM);
      visaJobCheck2.setPutForward(VisaEligibility.NO);
      visaJobCheck2.setTcEligibility(TcEligibilityAssessment.DISCUSS);
      visaJobCheck2.setAgeRequirement("18-45");
      visaJobCheck2.setLanguagesRequired(List.of(342L, 346L));
      visaJobCheck2.setLanguagesThresholdMet(YesNo.NO);

      return Set.of(visaJobCheck1, visaJobCheck2);
  }

  private SalesforceJobOpp createJobOpp() {
      SalesforceJobOpp jobOpp = new SalesforceJobOpp();
      jobOpp.setCountryIsoCode("AF");
      jobOpp.setEmployerEntity(createEmployerEntity());
      jobOpp.setEvergreen(true);
      jobOpp.setPublishedDate(OffsetDateTime.now());
      jobOpp.setStage(JobOpportunityStage.JOB_OFFER);
      jobOpp.setSubmissionDueDate(LocalDate.of(2025, 1, 1));
      jobOpp.setHiringCommitment(5L);
      jobOpp.setEmployerHiredInternationally("Yes");

      return jobOpp;
  }

  private Employer createEmployerEntity() {
      Employer employer = new Employer();
      employer.setCountryIsoCode("AF");
      employer.setHasHiredInternationally(true);
      return employer;
  }

  private EducationLevel createEducationLevel() {
    EducationLevel educationLevel = new EducationLevel();
    educationLevel.setId(8138L);
    educationLevel.setEducationType(EducationType.BACHELOR);
    educationLevel.setLevel(100);
    educationLevel.setStatus(Status.ACTIVE);
    return educationLevel;
  }

  private EducationMajor createEducationMajor() {
      EducationMajor major = new EducationMajor();
      major.setId(8713L);
      major.setIscedCode(null); // todo - sm - key by isced_code instead of id
      major.setName("Accounting");
      major.setStatus(Status.ACTIVE);
      return major;
  }

  private SurveyType createSurveyType() {
      SurveyType surveyType = new SurveyType();
      surveyType.setName("Information Session");
      surveyType.setStatus(Status.ACTIVE);
      return surveyType;
  }

}
