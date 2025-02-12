-- change column type for employer_hired_internationally
ALTER TABLE salesforce_job_opp
    ALTER COLUMN employer_hired_internationally TYPE varchar(255)
        USING CASE
            WHEN employer_hired_internationally THEN 'true'
            ELSE 'false'
        END;
