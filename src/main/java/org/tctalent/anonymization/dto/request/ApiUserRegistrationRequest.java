package org.tctalent.anonymization.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.tctalent.anonymization.security.ApiAuthority;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApiUserRegistrationRequest {

  @NotBlank(message = "Owner name must not be blank")
  private String ownerName;

  @NotBlank(message = "Email must not be blank")
  @Email(message = "Email must be a valid email address")
  private String email;

  @NotEmpty(message = "At least one authority must be provided")
  private List<ApiAuthority> authorities;
}
