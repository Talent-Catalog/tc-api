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
import org.tctalent.anonymization.model.Country;
import org.tctalent.anonymization.model.Destination;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.model.IdentifiableDependant;

@Mapper(uses = {})
public interface EntityMapper {

  @Mapping(target = "countryIsoCode", source = "country", qualifiedByName = "mapCountryToIsoCode")
  @Mapping(target = "birthCountryIsoCode", source = "birthCountry", qualifiedByName = "mapCountryToIsoCode")
  @Mapping(target = "nationalityIsoCode", source = "nationality", qualifiedByName = "mapCountryToIsoCode")
  @Mapping(target = "drivingLicenseCountryIsoCode", source = "drivingLicenseCountry", qualifiedByName = "mapCountryToIsoCode")
  CandidateEntity anonymize(IdentifiableCandidate candidate);

  @Mapping(target = "countryIsoCode", source = "country", qualifiedByName = "mapCountryToIsoCode")
  CandidateDestination mapDestination(Destination destinations);

  @Mapping(target = "nationalityIsoCode", source = "nationality", qualifiedByName = "mapCountryToIsoCode")
  CandidateCitizenship mapDestination(
      org.tctalent.anonymization.model.CandidateCitizenship citizenship);

  @Mapping(source = "dob", target = "yearOfBirth", qualifiedByName = "extractYearFromLocalDate")
  CandidateDependant mapDependant(IdentifiableDependant dependant);

  @Mapping(target = "countryIsoCode", source = "country", qualifiedByName = "mapCountryToIsoCode")
  CandidateEducation mapEducation(
      org.tctalent.anonymization.model.CandidateEducation education);

  @Named("mapCountryToIsoCode")
  default String mapCountryToIsoCode(Country country) {
    return (country != null) ? country.getIsoCode() : null;
  }

  @Named("extractYearFromLocalDate")
  default Integer extractYearFromLocalDate(LocalDate dob) {
    return dob != null ? dob.getYear() : null;
  }

}
