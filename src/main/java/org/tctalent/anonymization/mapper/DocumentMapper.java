package org.tctalent.anonymization.mapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.tctalent.anonymization.domain.common.HowHeardAboutUs;
import org.tctalent.anonymization.domain.document.CandidateVisaJobCheck;
import org.tctalent.anonymization.domain.document.Dependant;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.CandidatePage;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.domain.document.CandidateDocument;
import org.tctalent.anonymization.model.IdentifiableCandidateVisaJobCheck;
import org.tctalent.anonymization.model.IdentifiableDependant;
import org.tctalent.anonymization.model.SurveyType;

@Mapper(
    componentModel = "spring",
    uses = {DateTimeMapper.class}
)
public interface DocumentMapper {

  /**
   * Maps a Mongo {@link CandidateDocument} to an Open API {@link Candidate} model.
   *
   * @param document the mongo candidate document
   * @return the corresponding open api model
   */
  Candidate toCandidateModel(CandidateDocument document);

  /**
   * Maps a Page of {@link Candidate} models to an Open API {@link CandidatePage} model.
   *
   * @param page the page of candidates
   * @return the corresponding open api model
   */
  CandidatePage toCandidateModelPage(Page<Candidate> page);

  /**
   * Maps from an {@link IdentifiableCandidate} model to an anonymised Mongo
   * {@link CandidateDocument}.
   *
   * @param model the identifiable candidate model
   * @return the corresponding anonymised mongo document
   */
  @Mapping(source = "contactConsentPartners", target = "contactConsentTcPartners")
  @Mapping(source = "partnerCandidate.publicId", target = "partnerPublicId")
  @Mapping(source = "dob", target = "yearOfBirth", qualifiedByName = "extractYearFromLocalDate")
  @Mapping(target = "id", ignore = true)
  @Mapping(source = "surveyType", target = "howHeardAboutUs", qualifiedByName = "mapSurveyTypeToHowHeardAboutUsString")
  CandidateDocument anonymize(IdentifiableCandidate model);

  @Mapping(source = "dob", target = "yearOfBirth", qualifiedByName = "extractYearFromLocalDate")
  Dependant mapDependant(IdentifiableDependant identifiableDependant);

  @Mapping(source = "tbbEligibility", target = "tcEligibility")
  CandidateVisaJobCheck mapVisaJobCheck(IdentifiableCandidateVisaJobCheck identifiableDependant);

  @Named("extractYearFromLocalDate")
  default Integer extractYearFromLocalDate(LocalDate dob) {
    return dob != null ? dob.getYear() : null;
  }

  @Named("mapSurveyTypeToHowHeardAboutUsString")
  default String mapSurveyTypeToHowHeardAboutUs(SurveyType surveyType) {
    if (surveyType == null) {
      return null;
    }
    String surveyTypeName = surveyType.getName();

    final Map<String, String> SURVEY_TYPE_MAPPING = new HashMap<>();
    SURVEY_TYPE_MAPPING.put("Online Google Search", HowHeardAboutUs.ONLINE_GOOGLE_SEARCH.name());
    SURVEY_TYPE_MAPPING.put("Facebook", HowHeardAboutUs.FACEBOOK.name());
    SURVEY_TYPE_MAPPING.put("Facebook - through an organisation", HowHeardAboutUs.FACEBOOK.name());
    SURVEY_TYPE_MAPPING.put("Instagram", HowHeardAboutUs.INSTAGRAM.name());
    SURVEY_TYPE_MAPPING.put("LinkedIn", HowHeardAboutUs.LINKEDIN.name());
    SURVEY_TYPE_MAPPING.put("X", HowHeardAboutUs.X.name());
    SURVEY_TYPE_MAPPING.put("WhatsApp", HowHeardAboutUs.WHATSAPP.name());
    SURVEY_TYPE_MAPPING.put("YouTube", HowHeardAboutUs.YOUTUBE.name());
    SURVEY_TYPE_MAPPING.put("Friend or colleague referral", HowHeardAboutUs.FRIEND_COLLEAGUE_REFERRAL.name());
    SURVEY_TYPE_MAPPING.put("From a friend", HowHeardAboutUs.FRIEND_COLLEAGUE_REFERRAL.name());
    SURVEY_TYPE_MAPPING.put("University or school referral", HowHeardAboutUs.UNIVERSITY_SCHOOL_REFERRAL.name());
    SURVEY_TYPE_MAPPING.put("Employer referral", HowHeardAboutUs.EMPLOYER_REFERRAL.name());
    SURVEY_TYPE_MAPPING.put("Event or webinar", HowHeardAboutUs.EVENT_WEBINAR.name());
    SURVEY_TYPE_MAPPING.put("Information Session", HowHeardAboutUs.INFORMATION_SESSION.name());
    SURVEY_TYPE_MAPPING.put("Community centre posting - flyers", HowHeardAboutUs.COMMUNITY_CENTRE_POSTING_FLYERS.name());
    SURVEY_TYPE_MAPPING.put("Outreach worker", HowHeardAboutUs.OUTREACH_WORKER.name());
    SURVEY_TYPE_MAPPING.put("NGO", HowHeardAboutUs.NGO.name());
    SURVEY_TYPE_MAPPING.put("UNHCR", HowHeardAboutUs.UNHCR.name());
    SURVEY_TYPE_MAPPING.put("US-Afghan", HowHeardAboutUs.US_AFGHAN.name());
    SURVEY_TYPE_MAPPING.put("ULYP", HowHeardAboutUs.ULYP.name());
    SURVEY_TYPE_MAPPING.put("Techfugees", HowHeardAboutUs.TECHFUGEES.name());
    SURVEY_TYPE_MAPPING.put("Al Ghurair Foundation", HowHeardAboutUs.AL_GHURAIR_FOUNDATION.name());
    SURVEY_TYPE_MAPPING.put("Other", HowHeardAboutUs.OTHER.name());
    String mappedValue = SURVEY_TYPE_MAPPING.get(surveyTypeName);
    try {
      return mappedValue;
    } catch (IllegalArgumentException e) {
      return "OTHER";
    }
  }
}
