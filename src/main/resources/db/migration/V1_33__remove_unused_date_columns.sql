
ALTER TABLE candidate DROP COLUMN IF EXISTS updated_date;

ALTER TABLE candidate_certification DROP COLUMN IF EXISTS created_date;
ALTER TABLE candidate_certification DROP COLUMN IF EXISTS updated_date;

ALTER TABLE candidate_citizenship DROP COLUMN IF EXISTS created_date;
ALTER TABLE candidate_citizenship DROP COLUMN IF EXISTS updated_date;

ALTER TABLE candidate_dependant DROP COLUMN IF EXISTS created_date;
ALTER TABLE candidate_dependant DROP COLUMN IF EXISTS updated_date;

ALTER TABLE candidate_destination DROP COLUMN IF EXISTS created_date;
ALTER TABLE candidate_destination DROP COLUMN IF EXISTS updated_date;

ALTER TABLE candidate_education DROP COLUMN IF EXISTS created_date;
ALTER TABLE candidate_education DROP COLUMN IF EXISTS updated_date;

ALTER TABLE candidate_exam DROP COLUMN IF EXISTS created_date;
ALTER TABLE candidate_exam DROP COLUMN IF EXISTS updated_date;

ALTER TABLE candidate_job_experience DROP COLUMN IF EXISTS created_date;
ALTER TABLE candidate_job_experience DROP COLUMN IF EXISTS updated_date;

ALTER TABLE candidate_language DROP COLUMN IF EXISTS created_date;
ALTER TABLE candidate_language DROP COLUMN IF EXISTS updated_date;

ALTER TABLE candidate_occupation DROP COLUMN IF EXISTS created_date;
ALTER TABLE candidate_occupation DROP COLUMN IF EXISTS updated_date;

ALTER TABLE candidate_skill DROP COLUMN IF EXISTS created_date;
ALTER TABLE candidate_skill DROP COLUMN IF EXISTS updated_date;

ALTER TABLE candidate_visa_check DROP COLUMN IF EXISTS created_date;
ALTER TABLE candidate_visa_check DROP COLUMN IF EXISTS updated_date;

ALTER TABLE candidate_visa_job_check DROP COLUMN IF EXISTS created_date;
ALTER TABLE candidate_visa_job_check DROP COLUMN IF EXISTS updated_date;

ALTER TABLE country DROP COLUMN IF EXISTS created_date;
ALTER TABLE country DROP COLUMN IF EXISTS updated_date;

ALTER TABLE education_level DROP COLUMN IF EXISTS created_date;
ALTER TABLE education_level DROP COLUMN IF EXISTS updated_date;

ALTER TABLE education_major DROP COLUMN IF EXISTS created_date;
ALTER TABLE education_major DROP COLUMN IF EXISTS updated_date;

ALTER TABLE employer DROP COLUMN IF EXISTS created_date;
ALTER TABLE employer DROP COLUMN IF EXISTS updated_date;

ALTER TABLE language DROP COLUMN IF EXISTS created_date;
ALTER TABLE language DROP COLUMN IF EXISTS updated_date;

ALTER TABLE language_level DROP COLUMN IF EXISTS created_date;
ALTER TABLE language_level DROP COLUMN IF EXISTS updated_date;

ALTER TABLE occupation DROP COLUMN IF EXISTS created_date;
ALTER TABLE occupation DROP COLUMN IF EXISTS updated_date;

ALTER TABLE salesforce_job_opp DROP COLUMN IF EXISTS created_date;
ALTER TABLE salesforce_job_opp DROP COLUMN IF EXISTS updated_date;

ALTER TABLE survey_type DROP COLUMN IF EXISTS created_date;
ALTER TABLE survey_type DROP COLUMN IF EXISTS updated_date;
