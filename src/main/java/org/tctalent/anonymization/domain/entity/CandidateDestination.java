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
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tctalent.anonymization.domain.common.YesNoUnsure;

// todo - sm - check this - it's called Destination in the API schema
@Getter
@Setter
@Entity
@Table(name = "candidate_destination")
@SequenceGenerator(name = "seq_gen", sequenceName = "candidate_destination_id_seq", allocationSize = 1)
@NoArgsConstructor
public class CandidateDestination extends AbstractDomainEntity<Long>
        implements Comparable<CandidateDestination> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private CandidateEntity candidate;

    // Store the isoCode directly instead of a foreign key reference
    @Column(name = "country_iso_code", nullable = false)
    private String countryIsoCode;

    @Enumerated(EnumType.STRING)
    private YesNoUnsure interest;

    private String notes;

    @Override
    public int compareTo(CandidateDestination o) {
        if (countryIsoCode == null) {
            return o.countryIsoCode == null ? 0 : -1;
        }
        return countryIsoCode.compareTo(o.countryIsoCode);
    }
}
