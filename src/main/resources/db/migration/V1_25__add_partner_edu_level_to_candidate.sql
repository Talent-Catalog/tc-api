-- adds a new column "partner_edu_level" to candidate
-- and removes the old column "partner_edu_level_id"
ALTER TABLE candidate
    ADD COLUMN partner_edu_level INT;

ALTER TABLE candidate
    DROP COLUMN partner_edu_level_id;
