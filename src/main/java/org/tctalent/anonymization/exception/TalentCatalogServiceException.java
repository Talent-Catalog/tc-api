package org.tctalent.anonymization.exception;

import org.springframework.lang.NonNull;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Encapsulates exceptions received from calling the API of the core talent catalog.
 * <p/>
 * These are processed into ProblemDetail's in the GlobalExceptionHandler.
 *
 * @author John Cameron
 */
public class TalentCatalogServiceException extends RuntimeException {
    public TalentCatalogServiceException(@NonNull HttpClientErrorException cause) {
        super(cause);
    }

    public HttpClientErrorException getCause() {
        return (HttpClientErrorException) super.getCause();
    }
}
