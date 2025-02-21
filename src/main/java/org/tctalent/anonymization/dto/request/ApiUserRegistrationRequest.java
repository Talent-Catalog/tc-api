package org.tctalent.anonymization.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiUserRegistrationRequest {
  private String ownerName;
  private String email;
  private List<String> authorities;
}
