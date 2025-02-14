-- Add name and langauge level columns to candidate_language table
ALTER TABLE public.candidate_language
    ADD COLUMN name VARCHAR(255);

ALTER TABLE public.candidate_language
    ADD COLUMN written_level VARCHAR(255);

ALTER TABLE public.candidate_language
    ADD COLUMN spoken_level VARCHAR(255);

-- And remove language_id, written_level_id and spoken_level_id column
ALTER TABLE public.candidate_language
    DROP COLUMN language_id;

ALTER TABLE public.candidate_language
    DROP COLUMN written_level_id;

ALTER TABLE public.candidate_language
    DROP COLUMN spoken_level_id;
