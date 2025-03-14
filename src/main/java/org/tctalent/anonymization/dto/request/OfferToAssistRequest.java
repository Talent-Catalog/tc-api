/*
 * Copyright (c) 2024 Talent Catalog.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 */

package org.tctalent.anonymization.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.tctalent.anonymization.model.OfferToAssistCandidatesRequest;

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
public class OfferToAssistRequest extends OfferToAssistCandidatesRequest {

    /**
     * Copy constructor given an OfferToAssistCandidatesRequest
     * @param request Request through the public API
     */
    public OfferToAssistRequest(@NonNull OfferToAssistCandidatesRequest request) {
        setAdditionalNotes(request.getAdditionalNotes());
        setCandidates(request.getCandidates());
        setReason(request.getReason());
    }

    /**
     * Partner (service provider) associated with offer.
     */
    @NonNull
    Long partnerId;
}
