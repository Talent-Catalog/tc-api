package org.tctalent.anonymization.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.tctalent.anonymization.domain.entity.CandidateEntity;
import org.tctalent.anonymization.model.Country;
import org.tctalent.anonymization.model.IdentifiableCandidate;

@Mapper(uses = {})
public interface EntityMapper {

  @Mapping(target = "countryIsoCode", source = "country", qualifiedByName = "mapCountryToIsoCode")
  @Mapping(target = "birthCountryIsoCode", source = "birthCountry", qualifiedByName = "mapCountryToIsoCode")
  @Mapping(target = "nationalityIsoCode", source = "nationality", qualifiedByName = "mapCountryToIsoCode")
  @Mapping(target = "drivingLicenseCountryIsoCode", source = "drivingLicenseCountry", qualifiedByName = "mapCountryToIsoCode")
  CandidateEntity anonymize(IdentifiableCandidate candidate);

  @Named("mapCountryToIsoCode")
  default String mapCountryToIsoCode(Country country) {
    return (country != null) ? country.getIsoCode() : null;
  }

}
