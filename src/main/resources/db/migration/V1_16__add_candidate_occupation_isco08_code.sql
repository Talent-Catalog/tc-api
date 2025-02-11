-- Add isco08_code column to candidate_occupation table and remove country_id column
ALTER TABLE public.candidate_occupation
    ADD COLUMN isco08_code VARCHAR(8);

ALTER TABLE public.candidate_occupation
    ADD COLUMN name VARCHAR(255);

ALTER TABLE public.candidate_occupation
    DROP COLUMN occupation_id;

-- todo - sm - can't do the below yet because there are duplicated in isco08_codes
-- todo - sm - one code for multiple sub-occupations. Will address in a separate issue.
-- todo - sm - for now adding the occupation name directly to the candidate occupation table - above.
-- -- Add unique constraint to occupation table
-- ALTER TABLE public.occupation
--     ADD CONSTRAINT unique_isco_code UNIQUE (isco08_code);
--
-- -- Add foreign key constraints to candidate_occupation table
-- ALTER TABLE public.candidate_occupation
--     ADD CONSTRAINT fk_candidate_occupation_code
--         FOREIGN KEY (isco08_code) REFERENCES public.occupation(isco08_code)
--             ON DELETE SET NULL;
