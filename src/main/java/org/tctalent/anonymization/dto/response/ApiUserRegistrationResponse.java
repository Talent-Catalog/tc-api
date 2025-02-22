package org.tctalent.anonymization.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiUserRegistrationResponse {
  private String ownerName;
  private String email;
  private String apiKey; // The plain API key shown only once.
}
