/*
 * Copyright (c) 2023 Talent Beyond Boundaries.
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

package org.tctalent.anonymization.domain.common;

/**
 * Every candidate can only be in one status at a time.
 * <p/>
 * Only candidates with CandidateStatus.active are made visible to potential employers
 */
public enum CandidateStatus {

    /**
     * The candidate's data is ready to be shared with prospective employers.
     */
    ACTIVE,

    /**
     * The candidate has found employment themselves
     */
    AUTONOMOUS_EMPLOYMENT,

    /**
     * Candidate has been deleted. (The status of the candidate's corresponding User object should
     * also be set to deleted - ie {@link Status#DELETED}
     */
    DELETED,

    /**
     * Candidate has started registration but has not submitted - ie they are still in the
     * middle of completing their registration.
     */
    DRAFT,

    /**
     * Candidate is no longer looking for placement through TBB.
     */
    EMPLOYED,

    /**
     * Candidate's registration is not complete enough to be considered active.
     */
    INCOMPLETE,

    /**
     * The candidate is not eligible for TBB's support
     */
    INELIGIBLE,

    /**
     * Candidate has completed registration but TBB staff have not yet reviewed the registration.
     */
    PENDING,

    /**
     * We cannot contact candidate
     */
    UNREACHABLE,

    /**
     * The candidate has requested to be withdrawn from consideration.
     */
    WITHDRAWN,


}
