-- V2__create_batch_failed_items_table.sql

CREATE TABLE batch_failed_items (
    id BIGINT PRIMARY KEY,
    job_execution_id BIGINT NOT NULL,
    step_execution_id BIGINT NOT NULL,
    step_name VARCHAR(100),
    item_type VARCHAR(50),          -- e.g. "IdentifiableCandidate" or "CandidateEntity"
    item_public_id TEXT,            -- e.g. a single item or multiple public IDs for a failed chunk
    error_phase VARCHAR(20),        -- e.g. "PROCESSING" or "WRITING"
    error_message TEXT,             -- the exception message
    updated_date TIMESTAMP DEFAULT now(),
    created_date TIMESTAMP DEFAULT now()
);
