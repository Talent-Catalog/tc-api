package org.tctalent.anonymization.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.tctalent.anonymization.domain.entity.ApiUser;

/**
 * Authentication token that just wraps an ApiUser
 */
public class ApiKeyAuthentication extends AbstractAuthenticationToken {
  ApiUser apiUser;

  public ApiKeyAuthentication(ApiUser apiUser) {
    super(null);
    this.apiUser = apiUser;
    setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return apiUser;
  }
}
