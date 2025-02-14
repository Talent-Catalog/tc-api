-- adds a new column "partner_english_level" to candidate
-- and removes the old column "partner_english_level_id"
ALTER TABLE candidate
    ADD COLUMN partner_english_level VARCHAR(255);

ALTER TABLE candidate
    DROP COLUMN partner_english_level_id;
