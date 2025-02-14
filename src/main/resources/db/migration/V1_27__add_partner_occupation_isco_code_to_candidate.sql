-- Add partner_occupation_isco08_code and partner_occupation_name columns to candidate table
ALTER TABLE public.candidate
    ADD COLUMN partner_occupation_isco08_code VARCHAR(8);

ALTER TABLE public.candidate
    ADD COLUMN partner_occupation_name VARCHAR(255);

-- and remove partner_occupation_id column
ALTER TABLE public.candidate
    DROP COLUMN partner_occupation_id;

