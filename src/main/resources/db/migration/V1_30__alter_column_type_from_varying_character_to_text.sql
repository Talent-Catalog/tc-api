-- alter description column type to text
ALTER TABLE candidate
    ALTER COLUMN arrest_imprison_notes TYPE text,
    ALTER COLUMN avail_immediate_notes TYPE text,
    ALTER COLUMN candidate_message TYPE text,
    ALTER COLUMN conflict_notes TYPE text,
    ALTER COLUMN covid_vaccine_notes TYPE text,
    ALTER COLUMN crime_convict_notes TYPE text,
    ALTER COLUMN dest_limit_notes TYPE text,
    ALTER COLUMN english_assessment TYPE text,
    ALTER COLUMN family_move_notes TYPE text,
    ALTER COLUMN french_assessment TYPE text,
    ALTER COLUMN health_issues_notes TYPE text,
    ALTER COLUMN home_location TYPE text,
    ALTER COLUMN host_challenges TYPE text,
    ALTER COLUMN host_entry_legally_notes TYPE text,
    ALTER COLUMN host_entry_year_notes TYPE text,
    ALTER COLUMN int_recruit_rural_notes TYPE text,
    ALTER COLUMN left_home_notes TYPE text,
    ALTER COLUMN marital_status_notes TYPE text,
    ALTER COLUMN military_notes TYPE text,
    ALTER COLUMN residence_status_notes TYPE text,
    ALTER COLUMN survey_comment TYPE text,
    ALTER COLUMN unhcr_notes TYPE text,
    ALTER COLUMN unrwa_notes TYPE text,
    ALTER COLUMN visa_issues_notes TYPE text,
    ALTER COLUMN visa_reject_notes TYPE text,
    ALTER COLUMN work_abroad_notes TYPE text,
    ALTER COLUMN work_permit_desired_notes TYPE text,
    ALTER COLUMN partner_occupation_name TYPE text;

ALTER TABLE candidate_certification
    ALTER COLUMN name TYPE text,
    ALTER COLUMN institution TYPE text;

ALTER TABLE candidate_citizenship
    ALTER COLUMN notes TYPE text;

ALTER TABLE candidate_dependant
    ALTER COLUMN relation_other TYPE text;

ALTER TABLE candidate_destination
    ALTER COLUMN notes TYPE text;

ALTER TABLE candidate_education
    ALTER COLUMN institution TYPE text,
    ALTER COLUMN course_name TYPE text;

ALTER TABLE candidate_exam
    ALTER COLUMN other_exam TYPE text,
    ALTER COLUMN score TYPE text,
    ALTER COLUMN notes TYPE text;

ALTER TABLE candidate_job_experience
    ALTER COLUMN company_name TYPE text,
    ALTER COLUMN role TYPE text,
    ALTER COLUMN description TYPE text;

ALTER TABLE candidate_language
    ALTER COLUMN name TYPE text,
    ALTER COLUMN written_level TYPE text,
    ALTER COLUMN spoken_level TYPE text,
    ALTER COLUMN migration_language TYPE text;

ALTER TABLE candidate_occupation
    ALTER COLUMN name TYPE text;

ALTER TABLE candidate_skill
    ALTER COLUMN skill TYPE text,
    ALTER COLUMN time_period TYPE text;

ALTER TABLE candidate_visa_job_check
    ALTER COLUMN name TYPE text,
    ALTER COLUMN age_requirement TYPE text;

ALTER TABLE country
    ALTER COLUMN name TYPE text;

ALTER TABLE education_major
    ALTER COLUMN name TYPE text;

ALTER TABLE language
    ALTER COLUMN name TYPE text;

ALTER TABLE language_level
    ALTER COLUMN name TYPE text;

ALTER TABLE occupation
    ALTER COLUMN name TYPE text;

ALTER TABLE salesforce_job_opp
    ALTER COLUMN employer_hired_internationally TYPE text;

ALTER TABLE survey_type
    ALTER COLUMN name TYPE text;




