package org.tctalent.anonymization.mapper;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.CandidatePage;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.entity.mongo.CandidateDocument;

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
   * Maps a TC database {@link org.tctalent.anonymization.entity.db.Candidate} entity to an
   * anonymised Mongo {@link CandidateDocument}.
   *
   * @param entity the database candidate entity
   * @return the corresponding anonymised mongo document
   */
  @Mapping(source = "createdDate", target = "createdDate")
  CandidateDocument anonymize(org.tctalent.anonymization.entity.db.Candidate entity);

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
  CandidateDocument anonymize(IdentifiableCandidate model);

  @Named("extractYearFromLocalDate")
  default Integer extractYearFromLocalDate(LocalDate dob) {
    // todo confirm exception handling - try/catch - yes?
    return dob != null ? dob.getYear() : null;
  }

}
