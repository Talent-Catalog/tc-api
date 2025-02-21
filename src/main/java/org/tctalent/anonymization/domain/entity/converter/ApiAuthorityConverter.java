package org.tctalent.anonymization.domain.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.tctalent.anonymization.security.ApiAuthority;

@Converter
public class ApiAuthorityConverter implements AttributeConverter<Set<ApiAuthority>, String> {

  private static final String DELIMITER = ",";

  @Override
  public String convertToDatabaseColumn(Set<ApiAuthority> attribute) {
    if (attribute == null || attribute.isEmpty()) {
      return "";
    }
    return attribute.stream()
        .map(Enum::name)
        .collect(Collectors.joining(DELIMITER));
  }

  @Override
  public Set<ApiAuthority> convertToEntityAttribute(String dbData) {
    if (dbData == null || dbData.trim().isEmpty()) {
      return new HashSet<>();
    }
    return Arrays.stream(dbData.split(DELIMITER))
        .map(String::trim)
        .map(ApiAuthority::valueOf)
        .collect(Collectors.toSet());
  }
}
