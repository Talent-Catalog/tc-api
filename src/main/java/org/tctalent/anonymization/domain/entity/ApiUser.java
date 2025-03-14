package org.tctalent.anonymization.domain.entity;

import java.security.Principal;
import javax.security.auth.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.tctalent.anonymization.dto.response.Partner;

/**
 * Represents a user of the API. The ApiUser entity is used to store information about the user,
 * such as their name, email, API key, authorities, and expiration date.
 *
 * @author sadatmalik
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiUser implements Principal {
  @NonNull
  private Partner partner;

  @Override
  public String getName() {
    return partner.getName();
  }

  @Override
  public boolean implies(Subject subject) {
    return false;
  }
}
