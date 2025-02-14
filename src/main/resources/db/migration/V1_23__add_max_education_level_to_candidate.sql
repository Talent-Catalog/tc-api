-- adds a new column "max_education_level" to candidate
-- and removes the old column "max_education_level_id"
ALTER TABLE candidate
    ADD COLUMN max_education_level INT;

ALTER TABLE candidate
    DROP COLUMN max_education_level_id;
