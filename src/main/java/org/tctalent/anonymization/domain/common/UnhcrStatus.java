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

public enum UnhcrStatus {
    NO_RESPONSE("No response"),
    MANDATE_REFUGEE("Assessed by UNHCR as a mandate refugee (RSD)"),
    REGISTERED_ASYLUM("Registered with UNHCR as asylum seeker"),
    REGISTERED_STATELESS("Registered with UNHCR as stateless"),
    REGISTERED_STATUS_UNKNOWN("Registered with UNHCR but status unknown"),
    NOT_REGISTERED("Not registered"),
    UNSURE("Unsure"),
    NA("Not applicable");

    public final String label;

    UnhcrStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
