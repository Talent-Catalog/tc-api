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
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.domain.common.DocumentStatus;
import org.tctalent.anonymization.domain.common.FamilyRelations;
import org.tctalent.anonymization.domain.common.RiskLevel;
import org.tctalent.anonymization.domain.common.YesNo;
import org.tctalent.anonymization.domain.common.YesNoUnsure;

@Getter
@Setter
@Entity
@Table(name = "candidate_visa_check")
@SequenceGenerator(name = "seq_gen", sequenceName = "candidate_visa_check_id_seq", allocationSize = 1)
public class CandidateVisaCheck extends AbstractDomainEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private CandidateEntity candidate;

    // Store the isoCode directly instead of a foreign key reference
    @Size(max = 3)
    @Column(name = "country_iso_code", nullable = false, length = 3)
    private String countryIsoCode;

    @Enumerated(EnumType.STRING)
    private YesNo protection;

    @Enumerated(EnumType.STRING)
    private YesNo englishThreshold;

    @Enumerated(EnumType.STRING)
    private YesNo healthAssessment;

    @Enumerated(EnumType.STRING)
    private YesNo characterAssessment;

    @Enumerated(EnumType.STRING)
    private YesNo securityRisk;

    @Enumerated(EnumType.STRING)
    private RiskLevel overallRisk;

    @Enumerated(EnumType.STRING)
    private DocumentStatus validTravelDocs;

    @Enumerated(EnumType.STRING)
    private YesNoUnsure pathwayAssessment;

    @Enumerated(EnumType.STRING)
    private FamilyRelations destinationFamily;

    @Valid
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidateVisaCheck", cascade = CascadeType.ALL)
    private Set<CandidateVisaJobCheck> candidateVisaJobChecks = new HashSet<>();

    public void setCandidateVisaJobChecks(Set<CandidateVisaJobCheck> visaJobChecks) {
        this.candidateVisaJobChecks.clear();
        visaJobChecks.forEach(jobCheck -> {
            jobCheck.setCandidateVisaCheck(this);
        });
        this.candidateVisaJobChecks.addAll(visaJobChecks);
    }

}
