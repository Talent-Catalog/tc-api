-- Add country_iso_code column to employer table and remove country__id column
ALTER TABLE public.employer
    ADD COLUMN country_iso_code VARCHAR(3);

ALTER TABLE public.employer
    DROP COLUMN country_id;

-- Add foreign key constraints to employer table
ALTER TABLE public.employer
    ADD CONSTRAINT fk_employer_country
        FOREIGN KEY (country_iso_code) REFERENCES public.country(iso_code)
            ON DELETE SET NULL;
