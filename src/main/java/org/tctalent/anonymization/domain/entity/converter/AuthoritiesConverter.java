package org.tctalent.anonymization.domain.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class AuthoritiesConverter implements AttributeConverter<Set<String>, String> {

  private static final String DELIMITER = ",";

  @Override
  public String convertToDatabaseColumn(Set<String> attribute) {
    if (attribute == null || attribute.isEmpty()) {
      return "";
    }
    return String.join(DELIMITER, attribute);
  }

  @Override
  public Set<String> convertToEntityAttribute(String dbData) {
    if (dbData == null || dbData.trim().isEmpty()) {
      return new HashSet<>();
    }
    return Arrays.stream(dbData.split(DELIMITER))
        .map(String::trim)
        .collect(Collectors.toSet());
  }
}
