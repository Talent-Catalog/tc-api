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

package org.tctalent.anonymization.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@Entity
@Table(name = "employer")
@SequenceGenerator(name = "seq_gen", sequenceName = "employer_id_seq", allocationSize = 1)
public class Employer extends AbstractDomainEntity<Long> {

    // Store the isoCode directly instead of a foreign key reference
    @Size(max = 3)
    @Column(name = "country_iso_code", nullable = false, length = 3)
    private String countryIsoCode;

    /**
     * Indicates whether the employer has hired internationally or not.
     * Null if we don't know
     */
    @Nullable
    private Boolean hasHiredInternationally;

}
