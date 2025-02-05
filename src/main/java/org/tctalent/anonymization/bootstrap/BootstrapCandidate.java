package org.tctalent.anonymization.bootstrap;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.domain.common.AvailImmediateReason;
import org.tctalent.anonymization.domain.common.CandidateStatus;
import org.tctalent.anonymization.domain.common.DependantRelations;
import org.tctalent.anonymization.domain.common.EducationType;
import org.tctalent.anonymization.domain.common.Exam;
import org.tctalent.anonymization.domain.common.Gender;
import org.tctalent.anonymization.domain.common.HasPassport;
import org.tctalent.anonymization.domain.common.Registration;
import org.tctalent.anonymization.domain.common.Status;
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
import org.tctalent.anonymization.domain.entity.Country;
import org.tctalent.anonymization.domain.entity.EducationMajor;
import org.tctalent.anonymization.domain.entity.Language;
import org.tctalent.anonymization.domain.entity.LanguageLevel;
import org.tctalent.anonymization.domain.entity.Occupation;
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


      candidate.setGender(Gender.male);
      candidate.setStatus(CandidateStatus.active);
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

}
