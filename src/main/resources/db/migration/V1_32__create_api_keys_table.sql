-- Create api_user table
CREATE TABLE api_user (
    id BIGSERIAL PRIMARY KEY,
    api_key_hash VARCHAR(255) NOT NULL,
    owner_name VARCHAR(255) NOT NULL,
    authorities TEXT NOT NULL, -- e.g. "READ_CANDIDATE_DATA,SUBMIT_JOB_MATCHES"
    email VARCHAR(255),
    email_validated BOOLEAN DEFAULT FALSE,
    expires_at TIMESTAMP,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_used_at TIMESTAMP
);

-- For faster lookup of API keys by hash.
CREATE INDEX idx_api_user_api_key_hash ON api_user(api_key_hash);
