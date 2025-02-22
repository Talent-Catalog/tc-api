package org.tctalent.anonymization.security;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.tctalent.anonymization.domain.entity.ApiUser;
import org.tctalent.anonymization.repository.ApiUserRepository;

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

  private final ApiUserRepository apiUserRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public Authentication getAuthentication(HttpServletRequest request) {
    String presentedApiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
    if (presentedApiKey == null) {
      throw new BadCredentialsException("Invalid API Key");
    }

    ApiUser apiUser = findApiUserByApiKey(presentedApiKey);
    if (apiUser == null) {
      throw new BadCredentialsException("Invalid API key");
    }

    // Verify the API key against the stored hash
    if (!passwordEncoder.matches(presentedApiKey, apiUser.getApiKeyHash())) {
      throw new BadCredentialsException("Invalid API key");
    }
    // Convert the String authorities to GrantedAuthority objects
    List<SimpleGrantedAuthority> grantedAuthorities = apiUser.getAuthorities().stream()
        .map(apiAuthority -> new SimpleGrantedAuthority(apiAuthority.name()))
        .toList();

    return new ApiKeyAuthentication(presentedApiKey, grantedAuthorities);
  }

  private ApiUser findApiUserByApiKey(String apiKey) {
    // todo - sm this iterates over all API users and check if any match.
    //   before production can directly lookup API keys identifier as they are stored in a hash index.
    return apiUserRepository.findAll().stream()
        .filter(user -> passwordEncoder.matches(apiKey, user.getApiKeyHash()))
        .findFirst()
        .orElse(null);
  }

}
