-- Add survey_type to candidate table
ALTER TABLE public.candidate
    ADD COLUMN survey_type VARCHAR(255);

-- and remove survey_type_id column
ALTER TABLE public.candidate
    DROP COLUMN survey_type_id;
