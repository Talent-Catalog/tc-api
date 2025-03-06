package org.tctalent.anonymization.dto.response;


import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.domain.common.Role;
import org.tctalent.anonymization.domain.common.Status;
import org.tctalent.anonymization.model.Candidate;

@Getter
@Setter
public class User {
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private Role role;
  private boolean readOnly;
  private String passwordEnc;
  private Status status;
  private OffsetDateTime lastLogin;
  private String resetToken;
  private OffsetDateTime resetTokenIssuedDate;
  private OffsetDateTime passwordUpdatedDate;
  private boolean usingMfa;
  private String mfaSecret;
  private Candidate candidate;
  private String purpose;
}
