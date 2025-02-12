-- Add isco08_code column to candidate_occupation table and remove country_id column
ALTER TABLE public.candidate_occupation
    ADD COLUMN isco08_code VARCHAR(8);

ALTER TABLE public.candidate_occupation
    ADD COLUMN name VARCHAR(255);

ALTER TABLE public.candidate_occupation
    DROP COLUMN occupation_id;

