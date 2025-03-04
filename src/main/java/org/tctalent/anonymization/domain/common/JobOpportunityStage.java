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

package org.tctalent.anonymization.domain.common;

/**
 * Note that the string values of this enum MUST match the actual stage names for job
 * opportunities on Salesforce.
 * <p/>
 * See https://docs.google.com/document/d/1B6DmpYaONV_yNmyAqL76cu0TUQcpNgKtOmKELCkpRoc/edit#heading=h.qx7je1tuwoqv
 * <p/>
 * MODEL - Enum's with String labels. Looking up enum from label.
 */
public enum JobOpportunityStage {
        PROSPECT("Prospect"),
        BRIEFING("Briefing"),
        PITCHING("Pitching"),
        MOU("MOU"),
        IDENTIFYING_ROLES("Identifying roles"),
        CANDIDATE_SEARCH("Candidate search"),
        VISA_ELIGIBILITY("Visa eligibility"),
        CV_PREPARATION("CV preparation"),
        CV_REVIEW("CV review"),
        RECRUITMENT_PROCESS("Recruitment process"),
        JOB_OFFER("Job offer"),
        TRAINING("Training"),
        VISA_PREPARATION("Visa preparation"),
        POST_HIRE_ENGAGEMENT("Post hire engagement"),
        HIRING_COMPLETED("Hiring completed", true, true),
        INELIGIBLE_EMPLOYER("Ineligible employer", true, false),
        INELIGIBLE_OCCUPATION("Ineligible occupation", true, false),
        INELIGIBLE_REGION("Ineligible region", true, false),
        NO_INTEREST("No interest", true, false),
        NO_JOB_OFFER("No job offer", true, false),
        NO_PR_PATHWAY("No PR pathway", true, false),
        NO_SUITABLE_CANDIDATES("No suitable candidates", true, false),
        NO_VISA("No visa", true, false),
        TOO_EXPENSIVE("Too expensive", true, false),
        TOO_HIGH_WAGE("Too high wage", true, false),
        TOO_LONG("Too long", true, false),
        MOU_ISSUE("MOU issue", true, false),
        TRAINING_NOT_COMPLETED("Training not completed", true, false);

        private final String salesforceStageName;
        private final boolean closed;
        private final boolean won;


        JobOpportunityStage(String salesforceStageName, boolean closed, boolean won) {
                this.salesforceStageName = salesforceStageName;
                this.closed = closed;
                this.won = won;
        }

        JobOpportunityStage(String salesforceStageName) {
                this(salesforceStageName, false, false);
        }

        @Override
        public String toString() {
                return salesforceStageName;
        }

        public boolean isClosed() { return closed; }
        public boolean isWon() { return won; }

        public String getSalesforceStageName() {
                return salesforceStageName;
        }

        public static JobOpportunityStage textToEnum(String label) {
                for (JobOpportunityStage stage : JobOpportunityStage.values()) {
                        if (stage.salesforceStageName.equals(label)) {
                                return stage;
                        }
                }
                throw new IllegalArgumentException("Unrecognized JobOpportunityStage: " + label);
        }

}
