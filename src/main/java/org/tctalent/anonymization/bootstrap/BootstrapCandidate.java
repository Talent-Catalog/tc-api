package org.tctalent.anonymization.bootstrap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
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
import org.tctalent.anonymization.domain.common.NoteType;
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
import org.tctalent.anonymization.domain.entity.CandidateNote;
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
import org.tctalent.anonymization.repository.CandidateAuroraRepository;

@Component
@RequiredArgsConstructor
public class BootstrapCandidate implements CommandLineRunner {

  private final CandidateAuroraRepository candidateRepository;

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
      candidate.setArrestImprison(YesNoUnsure.No);
      candidate.setArrestImprisonNotes("No arrest or imprisonment");
      candidate.setAvailDate(LocalDate.of(2025, 1, 1));
      candidate.setAvailImmediate(YesNo.Yes);
      candidate.setAvailImmediateJobOps("Available immediately");
      candidate.setAvailImmediateReason(AvailImmediateReason.Other);
      candidate.setAvailImmediateNotes("Available immediately");
      candidate.setBirthCountry(createCountry(6178L, "United States", "US", Status.active));
      candidate.setCandidateCertifications(createCertifications());
      candidate.setCandidateCitizenships(createCandidateCitizenships());
      candidate.setCandidateDependants(createDependants());
      candidate.setCandidateDestinations(createDestinations());
      candidate.setCandidateEducations(createEducations());
      candidate.setCandidateExams(createExams());
      candidate.setCandidateJobExperiences(createJobExperiences());
      candidate.setCandidateLanguages(List.of(
          createCandidateLanguage(342L, "en", "English"),
          createCandidateLanguage(346L, "es", "Spanish")));
      candidate.setCandidateNotes(createCandidateNotes());
      candidate.setCandidateOccupations(List.of(
          createCandidateOccupation(8577L, "2411", "Accountant", Status.active),
          createCandidateOccupation(8484L, "3343", "Administrative assistant", Status.active)));
      candidate.setCandidateSkills(List.of(
          createCandidateSkill("Skill 1", "1 year"),
          createCandidateSkill("Skill 2", "2 years")));
      candidate.setCandidateVisaChecks(candidateVisaChecks());
      candidate.setCanDrive(YesNo.Yes);
      candidate.setCity("City 1");
      candidate.setConflict(YesNo.No);
      candidate.setConflictNotes("No conflict");
      candidate.setContactConsentTcPartners(true);
      candidate.setContactConsentRegistration(true);
      candidate.setCountry(createCountry(6180L, "Afghanistan", "AF", Status.active));
      candidate.setCovidVaccinated(YesNo.Yes);
      candidate.setCovidVaccinatedDate(LocalDate.of(2021, 1, 1));
      candidate.setCovidVaccineName("Vaccine 1");
      candidate.setCovidVaccineNotes("Notes 1");
      candidate.setCovidVaccinatedStatus(VaccinationStatus.Full);
      candidate.setCrimeConvict(YesNoUnsure.No);
      candidate.setCrimeConvictNotes("No crime or conviction");
      candidate.setDestLimit(YesNo.No);
      candidate.setDestLimitNotes("No destination limit");
      candidate.setDrivingLicenseCountry(createCountry(6178L, "United States", "US", Status.active));
      candidate.setDrivingLicenseExp(LocalDate.of(2025, 1, 1));
      candidate.setEnglishAssessment("English assessment");
      candidate.setEnglishAssessmentScoreIelts("7.5");
      candidate.setFamilyMove(YesNo.Yes);
      candidate.setFamilyMoveNotes("Family move notes");
      candidate.setFrenchAssessment("French assessment");
      candidate.setFrenchAssessmentScoreNclc(82L);
      candidate.setFullIntakeCompletedDate(OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC));
      candidate.setGender(Gender.male);
      candidate.setHealthIssues(YesNo.No);
      candidate.setHealthIssuesNotes("No health issues");
      candidate.setHomeLocation("Home location");
      candidate.setHostChallenges("Host challenges");
      candidate.setHostEntryLegally(YesNo.Yes);
      candidate.setHostEntryLegallyNotes("Host entry legally notes");
      candidate.setIeltsScore(new BigDecimal("7.5"));
      candidate.setIntRecruitReasons(List.of(IntRecruitReason.Experience, IntRecruitReason.Citizenship));
      candidate.setIntRecruitOther("Other reason");
      candidate.setIntRecruitRural(YesNoUnsure.Yes);
      candidate.setIntRecruitRuralNotes("Rural notes");
      candidate.setLeftHomeNotes("Left home notes");
      candidate.setLeftHomeReasons(List.of(LeftHomeReason.Safety, LeftHomeReason.Job));
      candidate.setMaritalStatus(MaritalStatus.Married);
      candidate.setMaxEducationLevel(createEducationLevel());
      candidate.setMediaWillingness("Media willingness");
      candidate.setMigrationEducationMajor(createEducationMajor());
      candidate.setMilitaryService(YesNo.Yes);
      candidate.setMilitaryWanted(YesNoUnsure.Yes);
      candidate.setMilitaryStart(LocalDate.of(2005, 1, 1));
      candidate.setMilitaryEnd(LocalDate.of(2010, 1, 1));
      candidate.setMilitaryNotes("Military notes");
      candidate.setMiniIntakeCompletedDate(OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC));
      candidate.setMonitoringEvaluationConsent(YesNo.Yes);
      candidate.setNationality(createCountry(6178L, "United States", "US", Status.active));
      candidate.setNumberDependants(2L);
      candidate.setPartnerRegistered(YesNoUnsure.Yes);
      candidate.setPartnerPublicId("partner-public-id-123");
      candidate.setPartnerEduLevel(createEducationLevel());
      candidate.setPartnerEnglish(YesNo.Yes);
      candidate.setPartnerEnglishLevel(createLanguageLevel());
      candidate.setPartnerIelts(IeltsStatus.YesAcademic);
      candidate.setPartnerIeltsScore("7.5");
      candidate.setPartnerIeltsYr(2010L);
      candidate.setPartnerOccupation(createOccupation(8577L, "2411", "Accountant"));
      candidate.setResidenceStatus(ResidenceStatus.LegalRes);
      candidate.setResidenceStatusNotes("Legal residence status notes");
      candidate.setReturnedHome(YesNoUnsure.Yes);
      candidate.setReturnedHomeReason("Returned home reason");
      candidate.setReturnedHomeReasonNo("Returned home reason no");
      candidate.setReturnHomeSafe(YesNoUnsure.NoResponse);
      candidate.setReturnHomeFuture(YesNoUnsure.NoResponse);
      candidate.setReturnHomeWhen(LocalDate.of(2025, 1, 1));
      candidate.setResettleThird(YesNo.NoResponse);
      candidate.setResettleThirdStatus("Residence status");
      candidate.setState("State 1");
      candidate.setStatus(CandidateStatus.active);
      candidate.setSurveyType(createSurveyType());
      candidate.setSurveyComment("Survey comment");
      candidate.setUnhcrConsent(YesNo.Yes);
      candidate.setUnhcrNotes("UNHCR notes");
      candidate.setUnhcrNotRegStatus(NotRegisteredStatus.NA);
      candidate.setUnhcrRegistered(YesNoUnsure.Unsure);
      candidate.setUnhcrStatus(UnhcrStatus.NoResponse);
      candidate.setUnrwaNotes("UNRWA notes");
      candidate.setUnrwaRegistered(YesNoUnsure.Yes);
      candidate.setVisaIssues(YesNoUnsure.No);
      candidate.setVisaIssuesNotes("No visa issues");
      candidate.setVisaReject(YesNoUnsure.No);
      candidate.setVisaRejectNotes("No visa rejection");
      candidate.setWorkAbroad(YesNo.Yes);
      candidate.setWorkAbroadNotes("Work abroad notes");
      candidate.setWorkPermit(WorkPermit.No);
      candidate.setWorkPermitDesired(YesNoUnsure.Yes);
      candidate.setWorkPermitDesiredNotes("Work permit notes");
      candidate.setYearOfArrival(2010);
      candidate.setYearOfBirth(1990);

      // Save to database
      CandidateEntity savedCandidate = candidateRepository.save(candidate);
      System.out.println("Candidate saved with id: " + savedCandidate.getId());

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
      citizenship1.setHasPassport(HasPassport.ValidPassport);
      citizenship1.setPassportExp(LocalDate.of(2025, 1, 1));
      citizenship1.setNationality(createCountry(6180L, "Afghanistan", "AF", Status.active));
      citizenship1.setNotes("Notes 1");

      CandidateCitizenship citizenship2 = new CandidateCitizenship();
      citizenship2.setHasPassport(HasPassport.InvalidPassport);
      citizenship2.setNationality(createCountry(6178L, "United States", "US", Status.active));
      citizenship2.setNotes("Notes 2");

      return List.of(citizenship1, citizenship2);
    }

    private List<CandidateDependant> createDependants() {
      CandidateDependant dependant1 = new CandidateDependant();
      dependant1.setRelation(DependantRelations.Child);
      dependant1.setRelationOther("Adopted");
      dependant1.setYearOfBirth(2010);
      dependant1.setGender(Gender.female);
      dependant1.setRegistered(Registration.UNHCR);
      dependant1.setHealthConcern(YesNo.Yes);

      CandidateDependant dependant2 = new CandidateDependant();
      dependant2.setRelation(DependantRelations.Partner);
      dependant2.setRelationOther("Spouse");
      dependant2.setYearOfBirth(1985);
      dependant2.setGender(Gender.male);
      dependant2.setRegistered(Registration.UNRWA);
      dependant2.setHealthConcern(YesNo.No);

      return List.of(dependant1, dependant2);
    }

    private List<CandidateDestination> createDestinations() {
      CandidateDestination destination1 = new CandidateDestination();
      destination1.setCountry(createCountry(6180L, "Afghanistan", "AF", Status.active));
      destination1.setInterest(YesNoUnsure.Yes);
      destination1.setNotes("Notes 1");

      CandidateDestination destination2 = new CandidateDestination();
      destination2.setCountry(createCountry(6178L, "United States", "US", Status.active));
      destination2.setInterest(YesNoUnsure.No);
      destination2.setNotes("Notes 2");

      return List.of(destination1, destination2);
    }

    private List<CandidateEducation> createEducations() {
      CandidateEducation education1 = new CandidateEducation();
      education1.setEducationType(EducationType.Associate);
      education1.setCountry(createCountry(6180L, "Afghanistan", "AF", Status.active));
      education1.setEducationMajor(createEducationMajor(8713L, "0111", "Major 1", Status.active));
      education1.setLengthOfCourseYears(2);
      education1.setInstitution("Institution 1");
      education1.setCourseName("Course 1");
      education1.setYearCompleted(2010);
      education1.setIncomplete(false);

      CandidateEducation education2 = new CandidateEducation();
      education2.setEducationType(EducationType.Bachelor);
      education2.setCountry(createCountry(6178L, "United States", "US", Status.active));
      education2.setEducationMajor(createEducationMajor(8714L, "0112", "Major 2", Status.active));
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
      exam1.setExam(Exam.IELTSGen);
      exam1.setOtherExam("Other exam 1");
      exam1.setScore("7.5");
      exam1.setYear(2010L);
      exam1.setNotes("Notes 1");

      CandidateExam exam2 = new CandidateExam();
      exam2.setExam(Exam.TOEFL);
      exam2.setOtherExam("Other exam 2");
      exam2.setScore("100");
      exam2.setYear(2014L);
      exam2.setNotes("Notes 2");

      return List.of(exam1, exam2);
    }

    private List<CandidateJobExperience> createJobExperiences() {
      CandidateJobExperience jobExperience1 = new CandidateJobExperience();
      jobExperience1.setCountry(createCountry(6180L, "Afghanistan", "AF", Status.active));
      jobExperience1.setCandidateOccupation(createCandidateOccupation(8577L,"2411", "Accountant", Status.active));
      jobExperience1.setCompanyName("Company 1");
      jobExperience1.setRole("Role 1");
      jobExperience1.setStartDate(LocalDate.of(2010, 1, 1));
      jobExperience1.setEndDate(LocalDate.of(2014, 1, 1));
      jobExperience1.setFullTime(true);
      jobExperience1.setPaid(true);
      jobExperience1.setDescription("Description 1");

      CandidateJobExperience jobExperience2 = new CandidateJobExperience();
      jobExperience2.setCountry(createCountry(6178L, "United States", "US", Status.active));
      jobExperience2.setCandidateOccupation(createCandidateOccupation(8484L, "3343", "Administrative assistant", Status.active));
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
      candidateOccupation.setOccupation(createOccupation(id, number, s));
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
      candidateLanguage.setLanguage(createLanguage(id, isoCode, name));
      candidateLanguage.setWrittenLevel(createLanguageLevel());
      candidateLanguage.setSpokenLevel(createLanguageLevel());
      return candidateLanguage;
  }

  private Language createLanguage(long id, String isoCode, String name) {
      Language language = new Language();
      language.setId(id);
      language.setIsoCode(isoCode);
      language.setName(name);
      language.setStatus(Status.active);
      return language;
  }

  private LanguageLevel createLanguageLevel() {
      LanguageLevel languageLevel = new LanguageLevel();
      languageLevel.setId(351L);
      languageLevel.setLevel(30);
      languageLevel.setName("Full Professional Proficiency");
      languageLevel.setStatus(Status.active);
      return languageLevel;
  }

  private List<CandidateNote> createCandidateNotes() {
      CandidateNote note1 = new CandidateNote();
      note1.setTitle("Note 1");
      note1.setComment("Comment 1");
      note1.setNoteType(NoteType.candidate);

      CandidateNote note2 = new CandidateNote();
      note2.setTitle("Note 2");
      note2.setComment("Comment 2");
      note2.setNoteType(NoteType.system);

      return List.of(note1, note2);
  }

  private CandidateSkill createCandidateSkill(String skill, String timePeriod) {
    CandidateSkill candidateSkill = new CandidateSkill();
    candidateSkill.setSkill(skill);
    candidateSkill.setTimePeriod(timePeriod);
    return candidateSkill;
  }

  private List<CandidateVisaCheck> candidateVisaChecks() {
      CandidateVisaCheck visaCheck1 = new CandidateVisaCheck();
      visaCheck1.setCountry(createCountry(6180L, "Afghanistan", "AF", Status.active));
      visaCheck1.setProtection(YesNo.Yes);
      visaCheck1.setEnglishThreshold(YesNo.Yes);
      visaCheck1.setHealthAssessment(YesNo.Yes);
      visaCheck1.setCharacterAssessment(YesNo.Yes);
      visaCheck1.setSecurityRisk(YesNo.Yes);
      visaCheck1.setOverallRisk(RiskLevel.High);
      visaCheck1.setValidTravelDocs(DocumentStatus.Valid);
      visaCheck1.setPathwayAssessment(YesNoUnsure.Yes);
      visaCheck1.setDestinationFamily(FamilyRelations.AuntUncle);
      visaCheck1.setCandidateVisaJobChecks(createCandidateVisaJobChecks());

      CandidateVisaCheck visaCheck2 = new CandidateVisaCheck();
      visaCheck2.setCountry(createCountry(6178L, "United States", "US", Status.active));
      visaCheck2.setProtection(YesNo.No);
      visaCheck2.setEnglishThreshold(YesNo.No);
      visaCheck2.setHealthAssessment(YesNo.No);
      visaCheck2.setCharacterAssessment(YesNo.No);
      visaCheck2.setSecurityRisk(YesNo.No);
      visaCheck2.setOverallRisk(RiskLevel.Low);
      visaCheck2.setValidTravelDocs(DocumentStatus.Expired);
      visaCheck2.setPathwayAssessment(YesNoUnsure.No);
      visaCheck2.setDestinationFamily(FamilyRelations.Cousin);
      visaCheck2.setCandidateVisaJobChecks(createCandidateVisaJobChecks());

      return List.of(visaCheck1, visaCheck2);
  }

  private Set<CandidateVisaJobCheck> createCandidateVisaJobChecks() {
      CandidateVisaJobCheck visaJobCheck1 = new CandidateVisaJobCheck();
      visaJobCheck1.setJobOpp(createJobOpp());
      visaJobCheck1.setInterest(YesNo.Yes);
      visaJobCheck1.setQualification(YesNo.Yes);
      visaJobCheck1.setOccupation(createOccupation(8577L, "2411", "Accountant"));
      visaJobCheck1.setSalaryTsmit(YesNo.Yes);
      visaJobCheck1.setRegional(YesNo.Yes);
      visaJobCheck1.setEligible_494(YesNo.Yes);
      visaJobCheck1.setEligible_186(YesNo.Yes);
      visaJobCheck1.setEligibleOther(OtherVisas.OtherHum);
      visaJobCheck1.setPutForward(VisaEligibility.Yes);
      visaJobCheck1.setTcEligibility(TcEligibilityAssessment.Proceed);
      visaJobCheck1.setAgeRequirement("18-45");
      visaJobCheck1.setLanguagesRequired(List.of(342L, 346L));
      visaJobCheck1.setLanguagesThresholdMet(YesNo.Yes);

      CandidateVisaJobCheck visaJobCheck2 = new CandidateVisaJobCheck();
      visaJobCheck2.setJobOpp(createJobOpp());
      visaJobCheck2.setInterest(YesNo.No);
      visaJobCheck2.setQualification(YesNo.No);
      visaJobCheck2.setOccupation(createOccupation(8484L, "3343", "Administrative assistant"));
      visaJobCheck2.setSalaryTsmit(YesNo.No);
      visaJobCheck2.setRegional(YesNo.No);
      visaJobCheck2.setEligible_494(YesNo.No);
      visaJobCheck2.setEligible_186(YesNo.No);
      visaJobCheck2.setEligibleOther(OtherVisas.OtherHum);
      visaJobCheck2.setPutForward(VisaEligibility.No);
      visaJobCheck2.setTcEligibility(TcEligibilityAssessment.Discuss);
      visaJobCheck2.setAgeRequirement("18-45");
      visaJobCheck2.setLanguagesRequired(List.of(342L, 346L));
      visaJobCheck2.setLanguagesThresholdMet(YesNo.No);

      return Set.of(visaJobCheck1, visaJobCheck2);
  }

  private SalesforceJobOpp createJobOpp() {
      SalesforceJobOpp jobOpp = new SalesforceJobOpp();
      jobOpp.setCountry(createCountry(6180L, "Afghanistan", "AF", Status.active));
      jobOpp.setEmployerEntity(createEmployerEntity());
      jobOpp.setEvergreen(true);
      jobOpp.setPublishedDate(OffsetDateTime.now());
      jobOpp.setStage(JobOpportunityStage.jobOffer);
      jobOpp.setSubmissionDueDate(LocalDate.of(2025, 1, 1));
      jobOpp.setHiringCommitment(5L);
      jobOpp.setEmployerHiredInternationally(true);

      return jobOpp;
  }

  private Employer createEmployerEntity() {
      Employer employer = new Employer();
      employer.setCountry(createCountry(6180L, "Afghanistan", "AF", Status.active));
      employer.setHasHiredInternationally(true);
      return employer;
  }

  private EducationLevel createEducationLevel() {
    EducationLevel educationLevel = new EducationLevel();
    educationLevel.setId(8138L);
    educationLevel.setEducationType(EducationType.Bachelor);
    educationLevel.setLevel(100);
    educationLevel.setStatus(Status.active);
    return educationLevel;
  }

  private EducationMajor createEducationMajor() {
      EducationMajor major = new EducationMajor();
      major.setId(8713L);
      major.setIscedCode(null); // todo - sm - key by isced_code instead of id
      major.setName("Accounting");
      major.setStatus(Status.active);
      return major;
  }

  private SurveyType createSurveyType() {
      SurveyType surveyType = new SurveyType();
      surveyType.setId(1L); // todo - sm - as with other ids - this (or a public id) should be sent on the rest dtp to setup the anon db relationship correctly
      surveyType.setName("Information Session");
      surveyType.setStatus(Status.active);
      return surveyType;
  }

}
