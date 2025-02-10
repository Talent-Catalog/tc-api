-- Add country_iso_code column to candidate_destination table and remove country_id column
ALTER TABLE public.candidate_destination
    ADD COLUMN country_iso_code VARCHAR(3);

ALTER TABLE public.candidate_destination
    DROP COLUMN country_id;

-- Add foreign key constraints to candidate_destination table
ALTER TABLE public.candidate_destination
    ADD CONSTRAINT fk_candidate_destination_country
        FOREIGN KEY (country_iso_code) REFERENCES public.country(iso_code)
            ON DELETE SET NULL;
