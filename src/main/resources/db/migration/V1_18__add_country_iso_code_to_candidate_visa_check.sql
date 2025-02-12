-- Add country_iso_code column to candidate_visa_check table and remove country_id column
ALTER TABLE public.candidate_visa_check
    ADD COLUMN country_iso_code VARCHAR(3);

ALTER TABLE public.candidate_visa_check
    DROP COLUMN country_id;

-- Add foreign key constraints to candidate_visa_check table
ALTER TABLE public.candidate_visa_check
    ADD CONSTRAINT fk_candidate_visa_check_country
        FOREIGN KEY (country_iso_code) REFERENCES public.country(iso_code)
            ON DELETE SET NULL;
