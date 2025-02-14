-- Add country_iso_code column to candidate_education table and remove country_id column
ALTER TABLE public.candidate_education
    ADD COLUMN country_iso_code VARCHAR(3);

ALTER TABLE public.candidate_education
    DROP COLUMN country_id;

-- Add foreign key constraints to candidate_education table
ALTER TABLE public.candidate_education
    ADD CONSTRAINT fk_candidate_education_country
        FOREIGN KEY (country_iso_code) REFERENCES public.country(iso_code)
            ON DELETE SET NULL;
