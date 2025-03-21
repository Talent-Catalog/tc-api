package org.tctalent.anonymization.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.tctalent.anonymization.model.RegisterCandidateRequest;

/**
 * This request is a copy of the request that comes in on the public API, with the added
 * field of the partner that the request came from.
 * This will have been deduced from the authentication process on the public api service.
 *
 * @author John Cameron
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RegisterCandidateByPartnerRequest extends RegisterCandidateRequest {

    /**
     * Copy constructor given an RegisterCandidateRequest
     * @param request Request through the public API
     */
    public RegisterCandidateByPartnerRequest(@NonNull RegisterCandidateRequest request) {
        setRegistrationData(request.getRegistrationData());
    }

    /**
     * Partner (service provider) associated with offer.
     */
    @NonNull
    Long partnerId;
}
