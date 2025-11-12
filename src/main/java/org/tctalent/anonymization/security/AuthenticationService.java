package org.tctalent.anonymization.security;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.tctalent.anonymization.domain.entity.ApiUser;
import org.tctalent.anonymization.dto.response.Partner;
import org.tctalent.anonymization.logging.LogBuilder;
import org.tctalent.anonymization.service.TalentCatalogService;

/**
 * Authentic Service that checks whether the HTTP request contains the API Key header with a secret
 * or not. If the header is null or isn’t equal to secret, we throw a BadCredentialsException.
 * <p/>
 * If the request has the header, it performs the authentication, adds the secret to the security
 * context, and then passes the call to the next security filter. Our getAuthentication method is
 * quite simple – we just compare the API Key header and secret with an encrypted database value.
 *
 * @author sadatmalik
 * @see <a href="https://www.baeldung.com/spring-boot-api-key-secret">
 */
@Service
@Slf4j
public class AuthenticationService {

    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

    private final TalentCatalogService talentCatalogService;

    /**
     * Simple caffeine cache of apiKeys to ApiUser's - avoiding unnecessary calls back to TC server.
     * <p/>
     * Cache only successful lookups, with TTL + size bound
     */
    private final Cache<String, ApiUser> keyToUserCache = Caffeine.newBuilder()
        .maximumSize(10_000) // Max 10k entries
        .expireAfterWrite(Duration.ofMinutes(10)) // Entries expire after 10 minutes
        .build();

    public AuthenticationService(TalentCatalogService talentCatalogService) {
        this.talentCatalogService = talentCatalogService;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String presentedApiKey = normalise(request.getHeader(AUTH_TOKEN_HEADER_NAME));
        if (presentedApiKey == null || presentedApiKey.isEmpty()) {
          LogBuilder.builder(log)
              .action("Auth failed")
              .message("Missing API key header " + AUTH_TOKEN_HEADER_NAME)
              .logWarn();

          throw new BadCredentialsException("Invalid API Key (missing)");
        }

        ApiUser apiUser = keyToUserCache.getIfPresent(presentedApiKey);
        if (apiUser == null) {
            apiUser = findApiUserByApiKey(presentedApiKey);
            if (apiUser != null) {
                keyToUserCache.put(presentedApiKey, apiUser);
            }
            if (apiUser == null) {
              LogBuilder.builder(log)
                  .action("Auth failed")
                  .message("Unknown API key " + maskKey(presentedApiKey))
                  .logWarn();

              throw new BadCredentialsException("Invalid API Key (unknown)");
            }
        }

        LogBuilder.builder(log)
            .action("Auth success")
            .message("partnerId=" + apiUser.getPartner().getPartnerId() + " partnerName=" + apiUser.getPartner().getName())
            .logWarn();

        // Convert the String authorities to GrantedAuthority objects
        List<SimpleGrantedAuthority> grantedAuthorities =
            apiUser.getPartner().getPublicApiAuthorities().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new ApiKeyAuthentication(apiUser, grantedAuthorities);
    }

    /**
     * Looks up user associated with authentication key.
     *
     * @param apiKey API key
     * @return ApiUser matching apiKey or null if there is no match for given key
     */
    @Nullable
    private ApiUser findApiUserByApiKey(String apiKey) {
        //Get user from TC service
        if (!talentCatalogService.isLoggedIn()) {
            talentCatalogService.login();
        }
        Partner partner = talentCatalogService.findPartnerByPublicApiKey(apiKey);
        return partner == null ? null : new ApiUser(partner);
    }

    /**
     * Get current ApiUser
     *
     * @return Current Api user, may be empty
     */
    public Optional<ApiUser> getCurrentApiUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof ApiUser) {
            return Optional.of((ApiUser) auth.getPrincipal());
        }
        return Optional.empty();
    }

    // Normalize header string by trimming whitespace, return null if header is null
    // Fixes potential issues with leading/trailing spaces when testing API keys from Postman or curl
    private String normalise(String header) {
        return header == null ? null : header.trim();
    }

    public void evictKey(String apiKey) {
      keyToUserCache.invalidate(apiKey);
    }

    public void clearCache() {
      keyToUserCache.invalidateAll();
    }

    // Log-safe mask: "xxxx…last4"
    static String maskKey(String raw) {
      if (raw == null || raw.isEmpty()) {
        return "xxxx…";
      }
      String last4 = raw.length() <= 4 ? raw : raw.substring(raw.length() - 4);
      return "xxxx…" + last4;
    }

}
