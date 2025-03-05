package org.tctalent.anonymization.domain.entity;

import java.security.Principal;
import javax.security.auth.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
  private Long partnerId;

  @Override
  public String getName() {
    return partnerId.toString();
  }

  @Override
  public boolean implies(Subject subject) {
    return false;
  }
}
