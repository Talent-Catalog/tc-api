package org.tctalent.anonymization.mapper;

import java.time.LocalDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.tctalent.anonymization.domain.entity.CandidateCitizenship;
import org.tctalent.anonymization.domain.entity.CandidateDependant;
import org.tctalent.anonymization.domain.entity.CandidateDestination;
import org.tctalent.anonymization.domain.entity.CandidateEducation;
import org.tctalent.anonymization.domain.entity.CandidateEntity;
import org.tctalent.anonymization.domain.entity.CandidateJobExperience;
import org.tctalent.anonymization.domain.entity.CandidateLanguage;
import org.tctalent.anonymization.domain.entity.CandidateOccupation;
import org.tctalent.anonymization.domain.entity.CandidateVisaCheck;
import org.tctalent.anonymization.domain.entity.CandidateVisaJobCheck;
import org.tctalent.anonymization.domain.entity.Employer;
import org.tctalent.anonymization.domain.entity.SalesforceJobOpp;
import org.tctalent.anonymization.model.Country;
import org.tctalent.anonymization.model.Destination;
import org.tctalent.anonymization.model.EducationLevel;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.model.IdentifiableDependant;
import org.tctalent.anonymization.model.IdentifiablePartner;
import org.tctalent.anonymization.model.Language;
import org.tctalent.anonymization.model.LanguageLevel;
import org.tctalent.anonymization.model.Occupation;

@Mapper(uses = {})
public interface EntityMapper {

  @Mapping(target = "countryIsoCode", source = "country", qualifiedByName = "mapCountryToIsoCode")
  @Mapping(target = "birthCountryIsoCode", source = "birthCountry", qualifiedByName = "mapCountryToIsoCode")
  @Mapping(target = "nationalityIsoCode", source = "nationality", qualifiedByName = "mapCountryToIsoCode")
  @Mapping(target = "drivingLicenseCountryIsoCode", source = "drivingLicenseCountry", qualifiedByName = "mapCountryToIsoCode")
  @Mapping(target = "contactConsentTcPartners", source = "contactConsentPartners")
  @Mapping(target = "yearOfBirth", source = "dob", qualifiedByName = "extractYearFromLocalDate")
  @Mapping(target = "maxEducationLevel", source = "maxEducationLevel", qualifiedByName = "mapEducationLevelToCode")
  @Mapping(target = "partnerEduLevel", source = "partnerEduLevel", qualifiedByName = "mapEducationLevelToCode")
  @Mapping(target = "partnerEnglishLevel", source = "partnerEnglishLevel", qualifiedByName = "mapLanguageLevelToName")
  @Mapping(target = "partnerOccupationIsco08Code", source = "partnerOccupation", qualifiedByName = "mapOccupationToIscoCode")
  @Mapping(target = "partnerOccupationName", source = "partnerOccupation", qualifiedByName = "mapOccupationToName")
  @Mapping(target = "partnerPublicId", source = "partnerCandidate", qualifiedByName = "mapPartnerToPublicId")
  CandidateEntity anonymize(IdentifiableCandidate candidate);

  @Mapping(target = "countryIsoCode", source = "country", qualifiedByName = "mapCountryToIsoCode")
  CandidateDestination mapDestination(Destination destinations);

  @Mapping(target = "nationalityIsoCode", source = "nationality", qualifiedByName = "mapCountryToIsoCode")
  CandidateCitizenship mapDestination(
      org.tctalent.anonymization.model.CandidateCitizenship citizenship);

  @Mapping(target = "yearOfBirth", source = "dob", qualifiedByName = "extractYearFromLocalDate")
  CandidateDependant mapDependant(IdentifiableDependant dependant);

  @Mapping(target = "countryIsoCode", source = "country", qualifiedByName = "mapCountryToIsoCode")
  CandidateEducation mapEducation(
      org.tctalent.anonymization.model.CandidateEducation education);

  @Mapping(target = "countryIsoCode", source = "country", qualifiedByName = "mapCountryToIsoCode")
  CandidateJobExperience mapJobExperience(
      org.tctalent.anonymization.model.CandidateJobExperience jobExperience);

  @Mapping(target = "isco08Code", source = "occupation", qualifiedByName = "mapOccupationToIscoCode")
  @Mapping(target = "name", source = "occupation", qualifiedByName = "mapOccupationToName")
  CandidateOccupation mapOccupation(
      org.tctalent.anonymization.model.CandidateOccupation occupation);

  @Mapping(target = "name", source = "language", qualifiedByName = "mapLanguageToName")
  @Mapping(target = "writtenLevelName", source = "writtenLevel", qualifiedByName = "mapLanguageLevelToName")
  @Mapping(target = "spokenLevelName", source = "spokenLevel", qualifiedByName = "mapLanguageLevelToName")
  CandidateLanguage mapLanguage(
      org.tctalent.anonymization.model.CandidateLanguage language);

  @Mapping(target = "countryIsoCode", source = "country", qualifiedByName = "mapCountryToIsoCode")
  CandidateVisaCheck mapVisaCheck(
      org.tctalent.anonymization.model.IdentifiableCandidateVisaCheck visaCheck);

  @Mapping(target = "isco08Code", source = "occupation", qualifiedByName = "mapOccupationToIscoCode")
  @Mapping(target = "name", source = "occupation", qualifiedByName = "mapOccupationToName")
  @Mapping(target = "tcEligibility", source = "tbbEligibility")
  CandidateVisaJobCheck mapVisaJobCheck(
      org.tctalent.anonymization.model.IdentifiableCandidateVisaJobCheck visaJobCheck);

  @Mapping(target = "countryIsoCode", source = "country", qualifiedByName = "mapCountryToIsoCode")
  SalesforceJobOpp mapJobOpp(
      org.tctalent.anonymization.model.JobOpportunity jobOpportunity);

  @Mapping(target = "countryIsoCode", source = "country", qualifiedByName = "mapCountryToIsoCode")
  Employer mapEmployer(
      org.tctalent.anonymization.model.Employer employer);

  @Named("mapCountryToIsoCode")
  default String mapCountryToIsoCode(Country country) {
    return (country != null) ? country.getIsoCode() : null;
  }

  @Named("extractYearFromLocalDate")
  default Integer extractYearFromLocalDate(LocalDate dob) {
    return dob != null ? dob.getYear() : null;
  }

  @Named("mapOccupationToIscoCode")
  default String mapOccupationToIscoCode(Occupation occupation) {
    return (occupation != null) ? occupation.getIsco08Code() : null;
  }

  @Named("mapOccupationToName")
  default String mapOccupationToName(Occupation occupation) {
    return (occupation != null) ? occupation.getName() : null;
  }

  @Named("mapLanguageToName")
  default String mapLanguageToName(Language language) {
    return (language != null) ? language.getName() : null;
  }

  @Named("mapLanguageLevelToName")
  default String mapLanguageLevelToName(LanguageLevel level) {
    return (level != null) ? level.getName() : null;
  }

  @Named("mapEducationLevelToCode")
  default Integer mapEducationLevelToCode(EducationLevel level) {
    return (level != null) ? level.getLevel() : null;
  }

  @Named("mapPartnerToPublicId")
  default String mapPartnerToPublicId(IdentifiablePartner partner) {
    return (partner != null) ? partner.getPublicId() : null;
  }
}
