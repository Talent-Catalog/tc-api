package org.tctalent.anonymization.service;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.tctalent.anonymization.config.properties.TalentCatalogServiceProperties;
import org.tctalent.anonymization.dto.request.LoginRequest;
import org.tctalent.anonymization.dto.request.OfferToAssistRequest;
import org.tctalent.anonymization.dto.request.RegisterCandidateByPartnerRequest;
import org.tctalent.anonymization.dto.request.SavedSearchGetRequest;
import org.tctalent.anonymization.dto.response.JwtAuthenticationResponse;
import org.tctalent.anonymization.dto.response.Partner;
import org.tctalent.anonymization.dto.response.PublicIdPage;
import org.tctalent.anonymization.exception.TalentCatalogServiceException;
import org.tctalent.anonymization.model.IdentifiableCandidatePage;
import org.tctalent.anonymization.model.OfferToAssistCandidates201Response;
import org.tctalent.anonymization.model.RegisterCandidate201Response;

/**
 * @author John Cameron
 */
@Service
public class TalentCatalogServiceImpl implements TalentCatalogService {

    private final TalentCatalogServiceProperties properties;
    private JwtAuthenticationResponse credentials;

    private final RestClient restClient;
    private final long savedSearchId;

    public TalentCatalogServiceImpl(RestClient.Builder restClientBuilder,
        TalentCatalogServiceProperties properties) {
        this.properties = properties;
        this.restClient = restClientBuilder.baseUrl(properties.getApiUrl()).build();
        this.savedSearchId = properties.getSearchId();
    }

    @Override
    public OfferToAssistCandidates201Response create(OfferToAssistRequest request)
        throws RestClientException {
        try {
            return restClient.post()
                .uri("/ota")
                .contentType(APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                    credentials.getTokenType() + " " + credentials.getAccessToken())
                .body(request)
                .retrieve()
                .body(OfferToAssistCandidates201Response.class);
        } catch (HttpClientErrorException e) {
            //Check for logged out
            if (e.getStatusCode().isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
                credentials = null;
            }
            throw new TalentCatalogServiceException(e);
        }
    }

    @Override
    public void login() throws RestClientException {

        //Note that we don't catch exceptions inside the login call because those exceptions
        //occur inside the AuthenticationFilter are need to be processed there.
        //In particular, those exceptions will not be caught by the GlobalExceptionHandler.
        LoginRequest request = new LoginRequest();
        request.setUsername(properties.getUsername());
        request.setPassword(properties.getPassword());

        credentials = restClient.post()
            .uri("/auth/login")
            .contentType(APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(JwtAuthenticationResponse.class);
    }

    @Override
    public String fetchPageOfCandidateDataAsJson(int pageNumber) {
        try {
            SavedSearchGetRequest request = new SavedSearchGetRequest();
            request.setPageSize(100);
            request.setPageNumber(pageNumber);
            return restClient.post()
                .uri("/saved-search-candidate/" + savedSearchId + "/search-paged")
                .contentType(APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                    credentials.getTokenType() + " " + credentials.getAccessToken())
                .body(request)
                .retrieve()
                .body(String.class);
        } catch (HttpClientErrorException e) {
            //Check for logged out
            if (e.getStatusCode().isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
                credentials = null;
            }
            throw new TalentCatalogServiceException(e);
        }
    }

    @Override
    public IdentifiableCandidatePage fetchPageOfIdentifiableCandidateData(int pageNumber,
        int pageSize)
        throws RestClientException {
        try {
            SavedSearchGetRequest request = new SavedSearchGetRequest();
            request.setPageSize(pageSize);
            request.setPageNumber(pageNumber);
            return restClient.post()
                .uri("/saved-search-candidate/" + savedSearchId + "/search-paged")
                .contentType(APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                    credentials.getTokenType() + " " + credentials.getAccessToken())
                .body(request)
                .retrieve()
                .body(IdentifiableCandidatePage.class);
        } catch (HttpClientErrorException e) {
            //Check for logged out
            if (e.getStatusCode().isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
                credentials = null;
            }
            throw new TalentCatalogServiceException(e);
        }
    }

    @Override
    public IdentifiableCandidatePage fetchPageOfCandidateDataByListId(long listId, int pageNumber,
        int pageSize) throws RestClientException {
        try {
            SavedSearchGetRequest request = new SavedSearchGetRequest();
            request.setPageSize(pageSize);
            request.setPageNumber(pageNumber);

            return restClient.post()
                .uri("/saved-list-candidate/" + listId + "/search-paged")
                .contentType(APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                    credentials.getTokenType() + " " + credentials.getAccessToken())
                .body(request)
                .retrieve()
                .body(IdentifiableCandidatePage.class);
        } catch (HttpClientErrorException e) {
            //Check for logged out
            if (e.getStatusCode().isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
                credentials = null;
            }
            throw new TalentCatalogServiceException(e);
        }
    }

    @Override
    public PublicIdPage fetchPageOfCandidatePublicIdsByPublicListId(String publicListId, int pageNumber, int pageSize)
        throws RestClientException {
        try {
            // build the paged request
            SavedSearchGetRequest request = new SavedSearchGetRequest();
            request.setPageNumber(pageNumber);
            request.setPageSize(pageSize);

            // call the /public/{publicId}/public-ids-paged endpoint
            return restClient.post()
                .uri("/saved-list-candidate/public/" + publicListId + "/public-ids-paged")
                .contentType(APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                    credentials.getTokenType() + " " + credentials.getAccessToken())
                .body(request)
                .retrieve()
                .body(PublicIdPage.class);

        } catch (HttpClientErrorException e) {
            // if we’ve been logged out, reset creds so next call will re-authenticate
            if (e.getStatusCode().isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
                credentials = null;
            }
            throw new TalentCatalogServiceException(e);
        }

    }

    @Override
    @Nullable
    public Partner findPartnerByPublicApiKey(String apiKey) {
      try {
        return doFindPartnerByPublicApiKey(apiKey);
      } catch (HttpClientErrorException e) {
        //Check for logged out
        if (e.getStatusCode().isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
          // token likely expired – re-login and retry once
          login();
          return doFindPartnerByPublicApiKey(apiKey);
        }
        if (e.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
          // no partner found for this API key
          return null;
        }
        throw e;
      }
    }

  private Partner doFindPartnerByPublicApiKey(String apiKey) {
    return restClient.get()
        .uri("/partner/public-api-key/{apiKey}", apiKey) // safer than string concat
        .header(HttpHeaders.AUTHORIZATION,
            credentials.getTokenType() + " " + credentials.getAccessToken())
        .retrieve()
        .body(Partner.class);
  }

    @Override
    public boolean isLoggedIn() {
        return credentials != null;
    }

    @Override
    public RegisterCandidate201Response register(RegisterCandidateByPartnerRequest request)
        throws RestClientException {
        try {
            return restClient.post()
                .uri("/candidate/register-by-partner")
                .contentType(APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                    credentials.getTokenType() + " " + credentials.getAccessToken())
                .body(request)
                .retrieve()
                .body(RegisterCandidate201Response.class);
        } catch (HttpClientErrorException e) {
            //Check for logged out
            if (e.getStatusCode().isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
                credentials = null;
            }
            throw new TalentCatalogServiceException(e);
        }
    }
}
