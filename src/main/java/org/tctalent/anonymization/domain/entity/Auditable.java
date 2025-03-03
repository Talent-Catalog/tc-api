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

package org.tctalent.anonymization.domain.entity;

import java.time.OffsetDateTime;

/**
 * Interface implemented by objects who want to record audit information about when they were
 * created and when they were last updated.
 */
public interface Auditable {

    /**
     * Date time of object creation
     * @return Date time of object creation
     */
    OffsetDateTime getCreatedDate();

    /**
     * Date time of last update
     * @return Date time of last update
     */
    OffsetDateTime getUpdatedDate();

}
