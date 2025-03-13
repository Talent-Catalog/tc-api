package org.tctalent.anonymization.security;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.tctalent.anonymization.domain.entity.ApiUser;
import org.tctalent.anonymization.dto.response.Partner;
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
public class AuthenticationService {

    /**
     * Simple cache of apiKeys to ApiUser's - avoiding unnecessary calls back to TC server.
     * <p/>
     * Cache is only cleared when server is restarted.
     */
    private final Map<String, ApiUser> keyToUserCache = new HashMap<>();

    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

    private final TalentCatalogService talentCatalogService;

    public AuthenticationService(TalentCatalogService talentCatalogService) {
        this.talentCatalogService = talentCatalogService;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String presentedApiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (presentedApiKey == null) {
            throw new BadCredentialsException("Invalid API Key");
        }

        ApiUser apiUser = findApiUserByApiKey(presentedApiKey);
        if (apiUser == null) {
            throw new BadCredentialsException("Invalid API key");
        }

        // Convert the String authorities to GrantedAuthority objects
        List<SimpleGrantedAuthority> grantedAuthorities =
            apiUser.getPartner().getPublicApiAuthorities().stream()
                .map(SimpleGrantedAuthority::new).toList();

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

        ApiUser apiUser;

        //Get user from cache if we have already retrieved it
        if (keyToUserCache.containsKey(apiKey)) {
            apiUser = keyToUserCache.get(apiKey);
        } else {
            //Get user from TC service
            if (!talentCatalogService.isLoggedIn()) {
                talentCatalogService.login();
            }
            Partner partner = talentCatalogService.findPartnerByPublicApiKey(apiKey);
            apiUser = partner == null ? null : new ApiUser(partner);

            //Remember result in cache. Note that this can store nulls if the key is not recognized.
            keyToUserCache.put(apiKey, apiUser);
        }
        return apiUser;
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
}
