package org.tctalent.anonymization.security;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * An ApiKeyAuthentication object that converts the incoming API Key to a Spring Authentication.
 * The AbstractAuthenticationToken class implements the Spring Authentication interface,
 * representing the secret/principal for an authenticated request.
 * <p/>
 * In Spring Security, an Authentication object typically holds two important pieces of data:
 * <p/>
 * Principal: This is the identity of the entity (user, API key, etc.) making the request. In this
 * particular class, the API key itself is used as the principal.
 * <p/>
 * Credentials: These are the secret details used to verify the identity, such as a password or
 * token. In this implementation, the getCredentials() method returns null because the API key
 * (which acts as both the identity and the secret) is stored as the principal.
 * <p/>
 * In essence, the authentication token is being used to encapsulate the secret (in this case, the
 * API key) that proves the request’s authenticity, as well as to identify who is making the request.
 *
 * @see <a href="https://www.baeldung.com/spring-boot-api-key-secret">
 * @author sadatmalik
 */
public class ApiKeyAuthentication extends AbstractAuthenticationToken {
  private final String apiKey;

  public ApiKeyAuthentication(String apiKey, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.apiKey = apiKey;
    setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return apiKey;
  }
}
