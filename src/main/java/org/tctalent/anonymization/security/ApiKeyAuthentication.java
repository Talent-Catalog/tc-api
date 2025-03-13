package org.tctalent.anonymization.security;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.tctalent.anonymization.domain.entity.ApiUser;

/**
 * Authentication token that just wraps an ApiUser and their related authorities
 */
public class ApiKeyAuthentication extends AbstractAuthenticationToken {
  ApiUser apiUser;

  public ApiKeyAuthentication(ApiUser apiUser, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
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
