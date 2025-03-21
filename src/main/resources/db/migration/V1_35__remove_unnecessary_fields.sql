-- V1__drop_columns_from_candidate_table.sql

ALTER TABLE public.candidate
DROP COLUMN IF EXISTS english_assessment,
DROP COLUMN IF EXISTS french_assessment,
DROP COLUMN IF EXISTS avail_immediate_notes,
DROP COLUMN IF EXISTS arrest_imprison_notes,
DROP COLUMN IF EXISTS conflict_notes,
DROP COLUMN IF EXISTS covid_vaccine_notes,
DROP COLUMN IF EXISTS crime_convict_notes,
DROP COLUMN IF EXISTS dest_limit_notes,
DROP COLUMN IF EXISTS health_issues_notes,
DROP COLUMN IF EXISTS family_move_notes,
DROP COLUMN IF EXISTS host_entry_legally_notes,
DROP COLUMN IF EXISTS int_recruit_rural_notes,
DROP COLUMN IF EXISTS left_home_notes,
DROP COLUMN IF EXISTS marital_status_notes,
DROP COLUMN IF EXISTS military_notes,
DROP COLUMN IF EXISTS visa_issues_notes,
DROP COLUMN IF EXISTS visa_reject_notes,
DROP COLUMN IF EXISTS work_abroad_notes,
DROP COLUMN IF EXISTS home_location,
DROP COLUMN IF EXISTS host_challenges,
DROP COLUMN IF EXISTS work_permit_desired_notes,
DROP COLUMN IF EXISTS residence_status_notes,
DROP COLUMN IF EXISTS unrwa_notes,
DROP COLUMN IF EXISTS unhcr_notes,
DROP COLUMN IF EXISTS host_entry_year_notes,
DROP COLUMN IF EXISTS resettle_third_status,
DROP COLUMN IF EXISTS state,
DROP COLUMN IF EXISTS int_recruit_other,
DROP COLUMN IF EXISTS survey_comment,
DROP COLUMN IF EXISTS returned_home_reason,
DROP COLUMN IF EXISTS returned_home_reason_no,
DROP COLUMN IF EXISTS candidate_message;

-- V2__drop_notes_columns.sql

-- Drop 'notes' column from 'CandidateExam' table
ALTER TABLE public.candidate_exam
DROP COLUMN IF EXISTS notes;

-- Drop 'notes' column from 'CandidateDestination' table
ALTER TABLE public.candidate_destination
DROP COLUMN IF EXISTS notes;

-- Drop 'notes' column from 'CandidateCitizenship' table
ALTER TABLE public.candidate_citizenship
DROP COLUMN IF EXISTS notes;
