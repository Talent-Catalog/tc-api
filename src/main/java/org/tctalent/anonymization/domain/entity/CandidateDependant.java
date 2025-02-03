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
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tctalent.anonymization.domain.common.DependantRelations;
import org.tctalent.anonymization.domain.common.Gender;
import org.tctalent.anonymization.domain.common.Registration;
import org.tctalent.anonymization.domain.common.YesNo;


// todo - sm - check this - it is called Dependant in the API schema
@Getter
@Setter
@Entity
@Table(name = "candidate_dependant")
@SequenceGenerator(name = "seq_gen", sequenceName = "candidate_dependant_id_seq", allocationSize = 1)
@NoArgsConstructor
public class CandidateDependant extends AbstractDomainEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private CandidateEntity candidate;

    @Enumerated(EnumType.STRING)
    private DependantRelations relation;

    private String relationOther;

    private Integer yearOfBirth;

    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Registration registered;

    @Enumerated(EnumType.STRING)
    private YesNo healthConcern;

}
