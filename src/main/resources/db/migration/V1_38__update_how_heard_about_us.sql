-- Step 1: Add the new how_heard_about_us column to the candidate table
ALTER TABLE candidate
    ADD COLUMN how_heard_about_us VARCHAR(255);

update survey_type
set name = 'Friend or colleague referral'
where name = 'From a friend';

-- Merge 'Facebook - through an organisation' into 'Facebook'
update survey_type
set name = 'Facebook'
where name = 'Facebook - through an organisation';

-- Insert new values from HowHeardAboutUs enum that are not in the table
insert into survey_type (id,name, status) values (14,'Online Google search', 'active');
insert into survey_type (id,name, status) values (15,'Instagram', 'active');
insert into survey_type (id,name, status) values (16,'LinkedIn', 'active');
insert into survey_type (id,name, status) values (17,'X', 'active');
insert into survey_type (id,name, status) values (18,'WhatsApp', 'active');
insert into survey_type (id,name, status) values (19,'YouTube', 'active');
insert into survey_type (id,name, status) values (20,'University or school referral', 'active');
insert into survey_type (id,name, status) values (21,'Employer referral', 'active');
insert into survey_type (id,name, status) values (22,'Event or webinar', 'active');

-- Update candidate Facebook - through an organisation to Facebook
update candidate
set survey_type = 'Facebook'
where survey_type = 'Facebook - through an organisation';
