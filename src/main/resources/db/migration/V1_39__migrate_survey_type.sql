-- Step 2: Populate the new how_heard_about_us column based on the existing survey_type_id
UPDATE candidate
SET how_heard_about_us =
        CASE
            WHEN LOWER(TRIM(survey_type)) = 'online google search' THEN 'ONLINE_GOOGLE_SEARCH'
            WHEN LOWER(TRIM(survey_type)) = 'facebook' THEN 'FACEBOOK'
            WHEN LOWER(TRIM(survey_type)) = 'instagram' THEN 'INSTAGRAM'
            WHEN LOWER(TRIM(survey_type)) = 'linkedin' THEN 'LINKEDIN'
            WHEN LOWER(TRIM(survey_type)) = 'x' THEN 'X'
            WHEN LOWER(TRIM(survey_type)) = 'whatsapp' THEN 'WHATSAPP'
            WHEN LOWER(TRIM(survey_type)) = 'youtube' THEN 'YOUTUBE'
            WHEN LOWER(TRIM(survey_type)) = 'friend or colleague referral' THEN 'FRIEND_COLLEAGUE_REFERRAL'
            WHEN LOWER(TRIM(survey_type)) = 'university or school referral' THEN 'UNIVERSITY_SCHOOL_REFERRAL'
            WHEN LOWER(TRIM(survey_type)) = 'employer referral' THEN 'EMPLOYER_REFERRAL'
            WHEN LOWER(TRIM(survey_type)) = 'event or webinar' THEN 'EVENT_WEBINAR'
            WHEN LOWER(TRIM(survey_type)) = 'information session' THEN 'INFORMATION_SESSION'
            WHEN LOWER(TRIM(survey_type)) = 'community centre posting - flyers' THEN 'COMMUNITY_CENTRE_POSTING_FLYERS'
            WHEN LOWER(TRIM(survey_type)) = 'outreach worker' THEN 'OUTREACH_WORKER'
            WHEN LOWER(TRIM(survey_type)) = 'ngo' THEN 'NGO'
            WHEN LOWER(TRIM(survey_type)) = 'unhcr' THEN 'UNHCR'
            WHEN LOWER(TRIM(survey_type)) = 'other' THEN 'OTHER'
            ELSE 'OTHER'
            END;



-- Remove  facebook through an organisation record
delete from survey_type
where id = 5;
-- -- Step 3: Drop the old survey_type column from the candidate table
ALTER TABLE candidate
DROP COLUMN survey_type;

-- Step 4: Drop the survey_type table since it's no longer needed
DROP TABLE survey_type CASCADE;

