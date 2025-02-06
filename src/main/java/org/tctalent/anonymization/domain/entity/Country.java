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

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tctalent.anonymization.domain.common.Status;

@Entity
@Table(name = "country")
@SequenceGenerator(name = "seq_gen", sequenceName = "country_id_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
public class Country extends AbstractDomainEntity<Long> implements Comparable<Country>{

    /**
     * ISO code for this country
     */
    private String isoCode;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public int compareTo(Country other) {
        if (this.isoCode == null) {
            return other.getIsoCode() == null ? 0 : -1;
        }

        if (other.getIsoCode() == null) {
            return 1;
        }

        return this.isoCode.compareTo(other.getIsoCode());
    }

    @Override
    public String toString() {
        return "Country{" + "name='" + getName() +
            "', isoCode='" + isoCode + '\'' +
            '}';
    }
}
