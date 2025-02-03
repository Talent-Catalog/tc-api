package org.tctalent.anonymization.mapper;

import java.time.LocalDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.tctalent.anonymization.domain.document.CandidateVisaJobCheck;
import org.tctalent.anonymization.domain.document.Dependant;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.CandidatePage;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.domain.document.CandidateDocument;
import org.tctalent.anonymization.model.IdentifiableCandidateVisaJobCheck;
import org.tctalent.anonymization.model.IdentifiableDependant;

@Mapper(uses = {
    IdMapper.class,
    DateTimeMapper.class
})
public interface CandidateMapper {

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
  CandidateDocument anonymize(IdentifiableCandidate model);

  @Mapping(source = "dob", target = "yearOfBirth", qualifiedByName = "extractYearFromLocalDate")
  Dependant mapDependant(IdentifiableDependant identifiableDependant);

  @Mapping(source = "tbbEligibility", target = "tcEligibility")
  CandidateVisaJobCheck mapVisaJobCheck(IdentifiableCandidateVisaJobCheck identifiableDependant);

  @Named("extractYearFromLocalDate")
  default Integer extractYearFromLocalDate(LocalDate dob) {
    return dob != null ? dob.getYear() : null;
  }

}
