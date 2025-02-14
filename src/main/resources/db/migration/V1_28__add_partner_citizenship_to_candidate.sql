-- Adds the "partner_citizenship" column to the candidate table
ALTER TABLE candidate
    ADD COLUMN partner_citizenship VARCHAR(255);
