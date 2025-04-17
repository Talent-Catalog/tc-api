-- Step 1: Add the new how_heard_about_us column to the candidate table
ALTER TABLE candidate
    ADD COLUMN how_heard_about_us VARCHAR(255);

-- Step 2: Populate the new how_heard_about_us column based on the existing survey_type_id
UPDATE candidate
SET how_heard_about_us =
        CASE
            WHEN survey_type = 'Online Google search' THEN 'ONLINE_GOOGLE_SEARCH'
            WHEN survey_type = 'Facebook' THEN 'FACEBOOK'
            WHEN survey_type = 'Instagram' THEN 'INSTAGRAM'
            WHEN survey_type = 'LinkedIn' THEN 'LINKEDIN'
            WHEN survey_type = 'X' THEN 'X'
            WHEN survey_type = 'WhatsApp' THEN 'WHATSAPP'
            WHEN survey_type = 'YouTube' THEN 'YOUTUBE'
            WHEN survey_type = 'Friend or colleague referral' THEN 'FRIEND_COLLEAGUE_REFERRAL'
            WHEN survey_type = 'University or school referral' THEN 'UNIVERSITY_SCHOOL_REFERRAL'
            WHEN survey_type = 'Employer referral' THEN 'EMPLOYER_REFERRAL'
            WHEN survey_type = 'Event or webinar' THEN 'EVENT_WEBINAR'
            WHEN survey_type = 'Information Session' THEN 'INFORMATION_SESSION'
            WHEN survey_type = 'Community centre posting - flyers' THEN 'COMMUNITY_CENTRE_POSTING_FLYERS'
            WHEN survey_type = 'Outreach worker' THEN 'OUTREACH_WORKER'
            WHEN survey_type = 'NGO' THEN 'NGO'
            WHEN survey_type = 'UNHCR' THEN 'UNHCR'
            WHEN survey_type = 'Other' THEN 'OTHER'
            ELSE 'OTHER'
            END;

-- -- Step 3: Drop the old survey_type column from the candidate table
ALTER TABLE candidate
DROP COLUMN survey_type;

-- Step 4: Drop the survey_type table since it's no longer needed
DROP TABLE survey_type CASCADE;

