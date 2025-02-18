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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.domain.common.JobOpportunityStage;


@Getter
@Setter
@Entity
@Table(name = "salesforce_job_opp")
@SequenceGenerator(name = "seq_gen", sequenceName = "salesforce_job_opp_tc_job_id_seq", allocationSize = 1)
public class SalesforceJobOpp extends AbstractDomainEntity<Long> {

    // Store the isoCode directly instead of a foreign key reference
    @Size(max = 3)
    @Column(name = "country_iso_code", nullable = false, length = 3)
    private String countryIsoCode;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employer_id")
    private Employer employerEntity;

    private boolean evergreen;
    private OffsetDateTime publishedDate;

    @Enumerated(EnumType.STRING)
    private JobOpportunityStage stage;

    private LocalDate submissionDueDate;
    private Long hiringCommitment;
    private String employerHiredInternationally;

}
