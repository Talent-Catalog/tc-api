-- Add nationality_iso_code column to candidate_citizenship table and remove nationality_id column
ALTER TABLE public.candidate_citizenship
    ADD COLUMN nationality_iso_code VARCHAR(3);

ALTER TABLE public.candidate_citizenship
    DROP COLUMN nationality_id;

-- Add foreign key constraints to candidate_citizenship table
ALTER TABLE public.candidate_citizenship
    ADD CONSTRAINT fk_candidate_citizenship_nationality
        FOREIGN KEY (nationality_iso_code) REFERENCES public.country(iso_code)
            ON DELETE SET NULL;
