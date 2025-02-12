-- Drop the migration_education_major_id column from the candidate table
-- this is legacy data that will no longer be used
ALTER TABLE candidate
    DROP COLUMN migration_education_major_id;
