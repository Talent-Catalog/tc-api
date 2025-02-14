-- Add country_iso_code column to candidate_job_experience table and remove country_id column
ALTER TABLE public.candidate_job_experience
    ADD COLUMN country_iso_code VARCHAR(3);

ALTER TABLE public.candidate_job_experience
    DROP COLUMN country_id;

-- Add foreign key constraints to candidate_job_experience table
ALTER TABLE public.candidate_job_experience
    ADD CONSTRAINT fk_candidate_job_experience_country
        FOREIGN KEY (country_iso_code) REFERENCES public.country(iso_code)
            ON DELETE SET NULL;
