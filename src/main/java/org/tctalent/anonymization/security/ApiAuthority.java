package org.tctalent.anonymization.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ApiAuthority {
  READ_CANDIDATE_DATA,
  SUBMIT_JOB_MATCHES,
  OFFER_CANDIDATE_SERVICES,
  REGISTER_CANDIDATES,
  ADMIN;

  @JsonCreator
  public static ApiAuthority from(String value) {
    try {
      // Allow case-insensitive matching
      return ApiAuthority.valueOf(value.toUpperCase());
    } catch (Exception ex) {
      throw new IllegalArgumentException("Invalid authority value: " + value + ". Allowed values are: " +
          String.join(", ", getAllowedValues()));
    }
  }

  private static String[] getAllowedValues() {
    ApiAuthority[] values = ApiAuthority.values();
    String[] names = new String[values.length];
    for (int i = 0; i < values.length; i++) {
      names[i] = values[i].name();
    }
    return names;
  }

  @JsonValue
  public String toValue() {
    return this.name();
  }


}
