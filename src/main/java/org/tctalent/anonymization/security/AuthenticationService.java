package org.tctalent.anonymization.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Authentic Service that checks whether the HTTP request contains the API Key header with a secret
 * or not. If the header is null or isn’t equal to secret, we throw a BadCredentialsException.
 * <p/>
 * If the request has the header, it performs the authentication, adds the secret to the security
 * context, and then passes the call to the next security filter. Our getAuthentication method is
 * quite simple – we just compare the API Key header and secret with an encrypted database value.
 *
 * @see <a href="https://www.baeldung.com/spring-boot-api-key-secret">
 * @author sadatmalik
 */
public class AuthenticationService {

  private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
  private static final String AUTH_TOKEN = "tc-api-xxx-yyy-zzz";

  public static Authentication getAuthentication(HttpServletRequest request) {
    String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
    if (apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
      throw new BadCredentialsException("Invalid API Key");
    }

    return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
  }
}
