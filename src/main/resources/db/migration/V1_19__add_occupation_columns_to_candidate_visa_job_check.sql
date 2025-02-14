-- Add occupation isco08_code and name column to candidate_visa_job_check table and remove country_id column
ALTER TABLE public.candidate_visa_job_check
    ADD COLUMN isco08_code VARCHAR(8);

ALTER TABLE public.candidate_visa_job_check
    ADD COLUMN name VARCHAR(255);

ALTER TABLE public.candidate_visa_job_check
    DROP COLUMN occupation_id;
