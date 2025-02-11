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
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "candidate_language")
@SequenceGenerator(name = "seq_gen", sequenceName = "candidate_language_id_seq", allocationSize = 1)
@NoArgsConstructor
public class CandidateLanguage  extends AbstractDomainEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private CandidateEntity candidate;

    // Store the language name directly instead of a foreign key reference
    @Column(name = "name", nullable = false)
    private String name;

    // Store the written_level directly instead of a foreign key reference
    @Column(name = "written_level", nullable = true)
    private String writtenLevelName;

    // Store the spoken_level directly instead of a foreign key reference
    @Column(name = "spoken_level", nullable = true)
    private String spokenLevelName;

    private String migrationLanguage;

}
