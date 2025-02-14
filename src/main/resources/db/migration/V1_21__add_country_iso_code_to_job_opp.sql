-- Add country_iso_code column to salesforce_job_opp table and remove country_object_id column
ALTER TABLE public.salesforce_job_opp
    ADD COLUMN country_iso_code VARCHAR(3);

ALTER TABLE public.salesforce_job_opp
    DROP COLUMN country_object_id;

-- Add foreign key constraints to salesforce_job_opp table
ALTER TABLE public.salesforce_job_opp
    ADD CONSTRAINT fk_salesforce_job_opp_country
        FOREIGN KEY (country_iso_code) REFERENCES public.country(iso_code)
            ON DELETE SET NULL;
