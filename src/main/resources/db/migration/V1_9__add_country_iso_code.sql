-- Add country_iso_code column to candidate table and remove country_id column
ALTER TABLE public.candidate
    ADD COLUMN country_iso_code VARCHAR(3);

ALTER TABLE public.candidate
    DROP COLUMN country_id;

ALTER TABLE public.candidate
    ADD COLUMN birth_country_iso_code VARCHAR(3);

ALTER TABLE public.candidate
    DROP COLUMN birth_country_id;

ALTER TABLE public.candidate
    ADD COLUMN nationality_iso_code VARCHAR(3);

ALTER TABLE public.candidate
    DROP COLUMN nationality_id;

ALTER TABLE public.candidate
    ADD COLUMN driving_license_country_iso_code VARCHAR(3);

ALTER TABLE public.candidate
    DROP COLUMN driving_license_country_id;

-- Tidy up the country table
DELETE FROM public.country
    WHERE iso_code = 'CI' AND status = 'inactive';

DELETE FROM public.country
    WHERE iso_code IS NULL AND name = 'Urdu';

UPDATE public.country
    SET iso_code = 'XX'
    WHERE name = 'Unknown';

UPDATE public.country
    SET iso_code = 'ZZ'
    WHERE name = 'Stateless';

-- Add unique constraint to country table
ALTER TABLE public.country
    ADD CONSTRAINT unique_country_iso_code UNIQUE (iso_code);

-- Add foreign key constraints to candidate table
ALTER TABLE public.candidate
    ADD CONSTRAINT fk_candidate_country
        FOREIGN KEY (country_iso_code) REFERENCES public.country(iso_code)
            ON DELETE SET NULL;

ALTER TABLE public.candidate
    ADD CONSTRAINT fk_candidate_birth_country
        FOREIGN KEY (birth_country_iso_code) REFERENCES public.country(iso_code)
            ON DELETE SET NULL;

ALTER TABLE public.candidate
    ADD CONSTRAINT fk_candidate_nationality
        FOREIGN KEY (nationality_iso_code) REFERENCES public.country(iso_code)
            ON DELETE SET NULL;

ALTER TABLE public.candidate
    ADD CONSTRAINT fk_candidate_driving_license_country
        FOREIGN KEY (driving_license_country_iso_code) REFERENCES public.country(iso_code)
            ON DELETE SET NULL;
