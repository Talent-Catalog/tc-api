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

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "candidate_occupation")
@SequenceGenerator(name = "seq_gen", sequenceName = "candidate_occupation_id_seq", allocationSize = 1)
@NoArgsConstructor
public class CandidateOccupation extends AbstractDomainEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private CandidateEntity candidate;

    // Store the isco08Code directly instead of a foreign key reference
    @Size(max = 255)
    @Column(name = "isco08_code", nullable = true)
    private String isco08Code;

    // Store the occupation name directly instead of a foreign key reference
    @Column(name = "name", nullable = true)
    private String name;

    private Long yearsExperience;

    @Valid
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidateOccupation", cascade = CascadeType.ALL)
    private List<CandidateJobExperience> candidateJobExperiences = new ArrayList<>();

    public void setCandidateJobExperiences(List<CandidateJobExperience> experiences) {
        this.candidateJobExperiences.clear();
        experiences.forEach(experience -> {
            experience.setCandidateOccupation(this);
            experience.setCandidate(candidate);
        });
        this.candidateJobExperiences.addAll(experiences);
    }

}
