package org.tctalent.anonymization.security;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.tctalent.anonymization.domain.entity.ApiUser;
import org.tctalent.anonymization.service.TalentCatalogService;

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
@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

  private final TalentCatalogService talentCatalogService;

  public Authentication getAuthentication(HttpServletRequest request) {
    String presentedApiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
    if (presentedApiKey == null) {
      throw new BadCredentialsException("Invalid API Key");
    }

    ApiUser apiUser = findApiUserByApiKey(presentedApiKey);
    if (apiUser == null) {
      throw new BadCredentialsException("Invalid API key");
    }

    return new ApiKeyAuthentication(apiUser);
  }

  private ApiUser findApiUserByApiKey(String apiKey) {
    if (!talentCatalogService.isLoggedIn()) {
      talentCatalogService.login();
    }
    Long partnerId = talentCatalogService.findPartnerIdByPublicApiKey(apiKey);
    return partnerId == null ? null : new ApiUser(partnerId);
  }

  /**
   * Get current ApiUser
   * @return Current Api user, may be empty
   */
  public Optional<ApiUser> getCurrentApiUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.getPrincipal() instanceof ApiUser) {
      return Optional.of((ApiUser) auth.getPrincipal());
    }
    return Optional.empty();
  }
}
