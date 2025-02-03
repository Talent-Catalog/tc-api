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

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.tctalent.anonymization.domain.entity.converter.DelimitedIdConverter;
import org.tctalent.anonymization.entity.common.enums.OtherVisas;
import org.tctalent.anonymization.entity.common.enums.TcEligibilityAssessment;
import org.tctalent.anonymization.entity.common.enums.VisaEligibility;
import org.tctalent.anonymization.entity.common.enums.YesNo;
import org.tctalent.anonymization.entity.db.Occupation;
import org.tctalent.anonymization.entity.db.SalesforceJobOpp;

@Getter
@Setter
@Entity
@Table(name = "candidate_visa_job_check")
@SequenceGenerator(name = "seq_gen", sequenceName = "candidate_visa_job_check_id_seq", allocationSize = 1)
public class CandidateVisaJobCheck extends AbstractDomainEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_visa_check_id")
    private CandidateVisaCheck candidateVisaCheck;

    /**
     * Associated job opportunity
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_opp_id")
    SalesforceJobOpp jobOpp;

    @Enumerated(EnumType.STRING)
    private YesNo interest;

    @Enumerated(EnumType.STRING)
    private YesNo qualification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation_id")
    private Occupation occupation;

    @Enumerated(EnumType.STRING)
    private YesNo salaryTsmit;

    @Enumerated(EnumType.STRING)
    private YesNo regional;

    @Enumerated(EnumType.STRING)
    private YesNo eligible_494;

    @Enumerated(EnumType.STRING)
    private YesNo eligible_186;

    @Enumerated(EnumType.STRING)
    private OtherVisas eligibleOther;

    @Enumerated(EnumType.STRING)
    private VisaEligibility putForward;

    @Enumerated(EnumType.STRING)
    private TcEligibilityAssessment tcEligibility;

    private String ageRequirement;

    @Convert(converter = DelimitedIdConverter.class)
    private List<Long> languagesRequired;

    @Enumerated(EnumType.STRING)
    private YesNo languagesThresholdMet;

}
