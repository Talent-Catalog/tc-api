package org.tctalent.anonymization.mapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.tctalent.anonymization.domain.common.HowHeardAboutUs;
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

@Mapper(
    componentModel = "spring",
    uses = {}
)
public interface EntityMapper {

  @Mapping(target = "id", ignore = true)
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
  @Mapping(target = "howHeardAboutUs", source = "candidate", qualifiedByName = "mapEnumToHowHeardAboutUs")
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


  @Named("mapEnumToHowHeardAboutUs")
  default HowHeardAboutUs mapEnumToHowHeardAboutUs(IdentifiableCandidate candidate) {
    if (candidate.getSurveyType() == null || candidate.getSurveyType().getName() == null) {
      return null;
    }
    String surveyTypeName = candidate.getSurveyType().getName();

    final Map<String, HowHeardAboutUs> SURVEY_TYPE_MAPPING = new HashMap<>();
    SURVEY_TYPE_MAPPING.put("Online Google Search", HowHeardAboutUs.ONLINE_GOOGLE_SEARCH);
    SURVEY_TYPE_MAPPING.put("Facebook", HowHeardAboutUs.FACEBOOK);
    SURVEY_TYPE_MAPPING.put("Instagram", HowHeardAboutUs.INSTAGRAM);
    SURVEY_TYPE_MAPPING.put("LinkedIn", HowHeardAboutUs.LINKEDIN);
    SURVEY_TYPE_MAPPING.put("X", HowHeardAboutUs.X);
    SURVEY_TYPE_MAPPING.put("WhatsApp", HowHeardAboutUs.WHATSAPP);
    SURVEY_TYPE_MAPPING.put("YouTube", HowHeardAboutUs.YOUTUBE);
    SURVEY_TYPE_MAPPING.put("Friend or colleague referral", HowHeardAboutUs.FRIEND_COLLEAGUE_REFERRAL);
    SURVEY_TYPE_MAPPING.put("University or school referral", HowHeardAboutUs.UNIVERSITY_SCHOOL_REFERRAL);
    SURVEY_TYPE_MAPPING.put("Employer referral", HowHeardAboutUs.EMPLOYER_REFERRAL);
    SURVEY_TYPE_MAPPING.put("Event or webinar", HowHeardAboutUs.EVENT_WEBINAR);
    SURVEY_TYPE_MAPPING.put("Information Session", HowHeardAboutUs.INFORMATION_SESSION);
    SURVEY_TYPE_MAPPING.put("Community centre posting - flyers", HowHeardAboutUs.COMMUNITY_CENTRE_POSTING_FLYERS);
    SURVEY_TYPE_MAPPING.put("Outreach worker", HowHeardAboutUs.OUTREACH_WORKER);
    SURVEY_TYPE_MAPPING.put("NGO", HowHeardAboutUs.NGO);
    SURVEY_TYPE_MAPPING.put("UNHCR", HowHeardAboutUs.UNHCR);
    SURVEY_TYPE_MAPPING.put("Other", HowHeardAboutUs.OTHER);
    HowHeardAboutUs mappedValue = SURVEY_TYPE_MAPPING.get(surveyTypeName);
    try {
        return mappedValue;
    } catch (IllegalArgumentException e) {
      return HowHeardAboutUs.OTHER;
    }
  }
}
