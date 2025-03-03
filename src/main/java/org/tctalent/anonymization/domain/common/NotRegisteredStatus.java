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

public enum NotRegisteredStatus {
    NO_RESPONSE("No response"),
    WAS_REGISTERED("No longer registered, but was registered previously"),
    NEVER_REGISTERED("Never been registered"),
    REGISTERING("Attempted to register and pending"),
    UNSURE("Unsure"),
    NA("Not applicable");

    public final String label;

    NotRegisteredStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
