--
-- PostgreSQL database dump
--

-- Dumped from database version 15.10 (Debian 15.10-1.pgdg120+1)
-- Dumped by pg_dump version 15.10 (Debian 15.10-1.pgdg120+1)


--
-- Name: batch_job_execution; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.batch_job_execution (
    job_execution_id bigint NOT NULL,
    version bigint,
    job_instance_id bigint NOT NULL,
    create_time timestamp without time zone NOT NULL,
    start_time timestamp without time zone,
    end_time timestamp without time zone,
    status character varying(10),
    exit_code character varying(2500),
    exit_message character varying(2500),
    last_updated timestamp without time zone
);


--
-- Name: batch_job_execution_context; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.batch_job_execution_context (
    job_execution_id bigint NOT NULL,
    short_context character varying(2500) NOT NULL,
    serialized_context text
);


--
-- Name: batch_job_execution_params; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.batch_job_execution_params (
    job_execution_id bigint NOT NULL,
    parameter_name character varying(100) NOT NULL,
    parameter_type character varying(100) NOT NULL,
    parameter_value character varying(2500),
    identifying character(1) NOT NULL
);


--
-- Name: batch_job_execution_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.batch_job_execution_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: batch_job_instance; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.batch_job_instance (
    job_instance_id bigint NOT NULL,
    version bigint,
    job_name character varying(100) NOT NULL,
    job_key character varying(32) NOT NULL
);


--
-- Name: batch_job_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.batch_job_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: batch_step_execution; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.batch_step_execution (
    step_execution_id bigint NOT NULL,
    version bigint NOT NULL,
    step_name character varying(100) NOT NULL,
    job_execution_id bigint NOT NULL,
    create_time timestamp without time zone NOT NULL,
    start_time timestamp without time zone,
    end_time timestamp without time zone,
    status character varying(10),
    commit_count bigint,
    read_count bigint,
    filter_count bigint,
    write_count bigint,
    read_skip_count bigint,
    write_skip_count bigint,
    process_skip_count bigint,
    rollback_count bigint,
    exit_code character varying(2500),
    exit_message character varying(2500),
    last_updated timestamp without time zone
);


--
-- Name: batch_step_execution_context; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.batch_step_execution_context (
    step_execution_id bigint NOT NULL,
    short_context character varying(2500) NOT NULL,
    serialized_context text
);


--
-- Name: batch_step_execution_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.batch_step_execution_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: candidate; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.candidate (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    arrest_imprison character varying(255),
    arrest_imprison_notes character varying(255),
    asylum_year date,
    avail_date date,
    avail_immediate smallint,
    avail_immediate_job_ops character varying(255),
    avail_immediate_notes character varying(255),
    avail_immediate_reason character varying(255),
    can_drive character varying(255),
    candidate_message character varying(255),
    city character varying(255),
    conflict character varying(255),
    conflict_notes character varying(255),
    contact_consent_registration boolean,
    contact_consent_tc_partners boolean,
    covid_vaccinated character varying(255),
    covid_vaccinated_date date,
    covid_vaccinated_status character varying(255),
    covid_vaccine_name character varying(255),
    covid_vaccine_notes character varying(255),
    crime_convict character varying(255),
    crime_convict_notes character varying(255),
    dest_limit character varying(255),
    dest_limit_notes character varying(255),
    driving_license character varying(255),
    driving_license_exp date,
    english_assessment character varying(255),
    english_assessment_score_ielts character varying(255),
    family_move character varying(255),
    family_move_notes character varying(255),
    french_assessment character varying(255),
    french_assessment_score_nclc bigint,
    full_intake_completed_date timestamp(6) with time zone,
    gender character varying(255),
    health_issues character varying(255),
    health_issues_notes character varying(255),
    home_location character varying(255),
    host_challenges character varying(255),
    host_entry_legally character varying(255),
    host_entry_legally_notes character varying(255),
    host_entry_year integer,
    host_entry_year_notes character varying(255),
    ielts_score numeric(38,2),
    int_recruit_other character varying(255),
    int_recruit_reasons character varying(255),
    int_recruit_rural character varying(255),
    int_recruit_rural_notes character varying(255),
    left_home_notes character varying(255),
    left_home_reasons character varying(255),
    marital_status character varying(255),
    marital_status_notes character varying(255),
    media_willingness character varying(255),
    military_end date,
    military_notes character varying(255),
    military_service character varying(255),
    military_start date,
    military_wanted character varying(255),
    mini_intake_completed_date timestamp(6) with time zone,
    monitoring_evaluation_consent character varying(255),
    number_dependants bigint,
    partner_english character varying(255),
    partner_ielts character varying(255),
    partner_ielts_score character varying(255),
    partner_ielts_yr bigint,
    partner_public_id character varying(255),
    partner_registered character varying(255),
    public_id character varying(255) NOT NULL,
    resettle_third character varying(255),
    resettle_third_status character varying(255),
    residence_status character varying(255),
    residence_status_notes character varying(255),
    return_home_future character varying(255),
    return_home_safe character varying(255),
    return_home_when date,
    returned_home character varying(255),
    returned_home_reason character varying(255),
    returned_home_reason_no character varying(255),
    state character varying(255),
    status character varying(255),
    survey_comment character varying(255),
    unhcr_consent smallint,
    unhcr_not_reg_status character varying(255),
    unhcr_notes character varying(255),
    unhcr_registered character varying(255),
    unhcr_status character varying(255),
    unrwa_not_reg_status character varying(255),
    unrwa_notes character varying(255),
    unrwa_registered character varying(255),
    visa_issues character varying(255),
    visa_issues_notes character varying(255),
    visa_reject character varying(255),
    visa_reject_notes character varying(255),
    work_abroad character varying(255),
    work_abroad_notes character varying(255),
    work_permit character varying(255),
    work_permit_desired character varying(255),
    work_permit_desired_notes character varying(255),
    year_of_arrival integer,
    year_of_birth integer,
    birth_country_id bigint,
    country_id bigint,
    driving_license_country_id bigint,
    max_education_level_id bigint,
    migration_education_major_id bigint,
    nationality_id bigint,
    partner_edu_level_id bigint,
    partner_english_level_id bigint,
    partner_occupation_id bigint,
    survey_type_id bigint,
    CONSTRAINT candidate_arrest_imprison_check CHECK (((arrest_imprison)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[]))),
    CONSTRAINT candidate_avail_immediate_check CHECK (((avail_immediate >= 0) AND (avail_immediate <= 2))),
    CONSTRAINT candidate_avail_immediate_reason_check CHECK (((avail_immediate_reason)::text = ANY ((ARRAY['Family'::character varying, 'Health'::character varying, 'CurrentWork'::character varying, 'Studies'::character varying, 'Other'::character varying])::text[]))),
    CONSTRAINT candidate_can_drive_check CHECK (((can_drive)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_conflict_check CHECK (((conflict)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_covid_vaccinated_check CHECK (((covid_vaccinated)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_covid_vaccinated_status_check CHECK (((covid_vaccinated_status)::text = ANY ((ARRAY['NoResponse'::character varying, 'Full'::character varying, 'Partial'::character varying])::text[]))),
    CONSTRAINT candidate_crime_convict_check CHECK (((crime_convict)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[]))),
    CONSTRAINT candidate_dest_limit_check CHECK (((dest_limit)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_driving_license_check CHECK (((driving_license)::text = ANY ((ARRAY['NoResponse'::character varying, 'Valid'::character varying, 'Expired'::character varying, 'None'::character varying])::text[]))),
    CONSTRAINT candidate_family_move_check CHECK (((family_move)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_gender_check CHECK (((gender)::text = ANY ((ARRAY['male'::character varying, 'female'::character varying, 'other'::character varying])::text[]))),
    CONSTRAINT candidate_health_issues_check CHECK (((health_issues)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_host_entry_legally_check CHECK (((host_entry_legally)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_int_recruit_rural_check CHECK (((int_recruit_rural)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[]))),
    CONSTRAINT candidate_marital_status_check CHECK (((marital_status)::text = ANY ((ARRAY['NoResponse'::character varying, 'Married'::character varying, 'Engaged'::character varying, 'Defacto'::character varying, 'Single'::character varying, 'Divorced'::character varying, 'Separated'::character varying, 'Widower'::character varying])::text[]))),
    CONSTRAINT candidate_military_service_check CHECK (((military_service)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_military_wanted_check CHECK (((military_wanted)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[]))),
    CONSTRAINT candidate_monitoring_evaluation_consent_check CHECK (((monitoring_evaluation_consent)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_partner_english_check CHECK (((partner_english)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_partner_ielts_check CHECK (((partner_ielts)::text = ANY ((ARRAY['NoResponse'::character varying, 'YesGeneral'::character varying, 'YesAcademic'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[]))),
    CONSTRAINT candidate_partner_registered_check CHECK (((partner_registered)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[]))),
    CONSTRAINT candidate_resettle_third_check CHECK (((resettle_third)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_residence_status_check CHECK (((residence_status)::text = ANY ((ARRAY['NoResponse'::character varying, 'LegalRes'::character varying, 'IllegalRes'::character varying, 'Other'::character varying])::text[]))),
    CONSTRAINT candidate_return_home_future_check CHECK (((return_home_future)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[]))),
    CONSTRAINT candidate_return_home_safe_check CHECK (((return_home_safe)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[]))),
    CONSTRAINT candidate_returned_home_check CHECK (((returned_home)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[]))),
    CONSTRAINT candidate_status_check CHECK (((status)::text = ANY ((ARRAY['active'::character varying, 'autonomousEmployment'::character varying, 'deleted'::character varying, 'draft'::character varying, 'employed'::character varying, 'incomplete'::character varying, 'ineligible'::character varying, 'pending'::character varying, 'unreachable'::character varying, 'withdrawn'::character varying])::text[]))),
    CONSTRAINT candidate_unhcr_consent_check CHECK (((unhcr_consent >= 0) AND (unhcr_consent <= 2))),
    CONSTRAINT candidate_unhcr_not_reg_status_check CHECK (((unhcr_not_reg_status)::text = ANY ((ARRAY['NoResponse'::character varying, 'WasRegistered'::character varying, 'NeverRegistered'::character varying, 'Registering'::character varying, 'Unsure'::character varying, 'NA'::character varying])::text[]))),
    CONSTRAINT candidate_unhcr_registered_check CHECK (((unhcr_registered)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[]))),
    CONSTRAINT candidate_unhcr_status_check CHECK (((unhcr_status)::text = ANY ((ARRAY['NoResponse'::character varying, 'MandateRefugee'::character varying, 'RegisteredAsylum'::character varying, 'RegisteredStateless'::character varying, 'RegisteredStatusUnknown'::character varying, 'NotRegistered'::character varying, 'Unsure'::character varying, 'NA'::character varying])::text[]))),
    CONSTRAINT candidate_unrwa_not_reg_status_check CHECK (((unrwa_not_reg_status)::text = ANY ((ARRAY['NoResponse'::character varying, 'WasRegistered'::character varying, 'NeverRegistered'::character varying, 'Registering'::character varying, 'Unsure'::character varying, 'NA'::character varying])::text[]))),
    CONSTRAINT candidate_unrwa_registered_check CHECK (((unrwa_registered)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[]))),
    CONSTRAINT candidate_visa_issues_check CHECK (((visa_issues)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[]))),
    CONSTRAINT candidate_visa_reject_check CHECK (((visa_reject)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[]))),
    CONSTRAINT candidate_work_abroad_check CHECK (((work_abroad)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_work_permit_check CHECK (((work_permit)::text = ANY ((ARRAY['NoResponse'::character varying, 'YesNotDesired'::character varying, 'YesDesired'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_work_permit_desired_check CHECK (((work_permit_desired)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[])))
);


--
-- Name: candidate_certification; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.candidate_certification (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    date_completed date,
    institution character varying(255),
    name character varying(255),
    candidate_id bigint
);


--
-- Name: candidate_certification_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.candidate_certification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: candidate_citizenship; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.candidate_citizenship (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    has_passport character varying(255),
    notes character varying(255),
    passport_exp date,
    candidate_id bigint,
    nationality_id bigint,
    CONSTRAINT candidate_citizenship_has_passport_check CHECK (((has_passport)::text = ANY ((ARRAY['NoResponse'::character varying, 'ValidPassport'::character varying, 'InvalidPassport'::character varying, 'NoPassport'::character varying])::text[])))
);


--
-- Name: candidate_citizenship_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.candidate_citizenship_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: candidate_dependant; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.candidate_dependant (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    gender smallint,
    health_concern character varying(255),
    registered character varying(255),
    relation character varying(255),
    relation_other character varying(255),
    year_of_birth integer,
    candidate_id bigint,
    CONSTRAINT candidate_dependant_gender_check CHECK (((gender >= 0) AND (gender <= 2))),
    CONSTRAINT candidate_dependant_health_concern_check CHECK (((health_concern)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_dependant_registered_check CHECK (((registered)::text = ANY ((ARRAY['NoResponse'::character varying, 'UNHCR'::character varying, 'UNRWA'::character varying, 'Neither'::character varying, 'NA'::character varying])::text[]))),
    CONSTRAINT candidate_dependant_relation_check CHECK (((relation)::text = ANY ((ARRAY['NoResponse'::character varying, 'Partner'::character varying, 'Child'::character varying, 'Parent'::character varying, 'Sibling'::character varying, 'AuntUncle'::character varying, 'Grandparent'::character varying, 'Cousin'::character varying, 'Other'::character varying])::text[])))
);


--
-- Name: candidate_dependant_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.candidate_dependant_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: candidate_destination; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.candidate_destination (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    interest character varying(255),
    notes character varying(255),
    candidate_id bigint,
    country_id bigint,
    CONSTRAINT candidate_destination_interest_check CHECK (((interest)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[])))
);


--
-- Name: candidate_destination_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.candidate_destination_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: candidate_education; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.candidate_education (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    course_name character varying(255),
    education_type character varying(255),
    incomplete boolean,
    institution character varying(255),
    length_of_course_years integer,
    year_completed integer,
    candidate_id bigint,
    country_id bigint,
    major_id bigint,
    CONSTRAINT candidate_education_education_type_check CHECK (((education_type)::text = ANY ((ARRAY['Associate'::character varying, 'Vocational'::character varying, 'Bachelor'::character varying, 'Masters'::character varying, 'Doctoral'::character varying])::text[])))
);


--
-- Name: candidate_education_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.candidate_education_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: candidate_exam; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.candidate_exam (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    exam character varying(255),
    notes character varying(255),
    other_exam character varying(255),
    score character varying(255),
    year bigint,
    candidate_id bigint,
    CONSTRAINT candidate_exam_exam_check CHECK (((exam)::text = ANY ((ARRAY['NoResponse'::character varying, 'OET'::character varying, 'OETRead'::character varying, 'OETList'::character varying, 'OETLang'::character varying, 'IELTSGen'::character varying, 'IELTSAca'::character varying, 'TOEFL'::character varying, 'Other'::character varying])::text[])))
);


--
-- Name: candidate_exam_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.candidate_exam_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: candidate_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.candidate_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: candidate_job_experience; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.candidate_job_experience (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    company_name character varying(255),
    description character varying(255),
    end_date date,
    full_time boolean,
    paid boolean,
    role character varying(255),
    start_date date,
    candidate_id bigint,
    candidate_occupation_id bigint,
    country_id bigint
);


--
-- Name: candidate_job_experience_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.candidate_job_experience_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: candidate_language; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.candidate_language (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    migration_language character varying(255),
    candidate_id bigint,
    language_id bigint,
    spoken_level_id bigint,
    written_level_id bigint
);


--
-- Name: candidate_language_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.candidate_language_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: candidate_note; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.candidate_note (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    comment character varying(255),
    note_type character varying(255),
    title character varying(255),
    candidate_id bigint,
    CONSTRAINT candidate_note_note_type_check CHECK (((note_type)::text = ANY ((ARRAY['admin'::character varying, 'candidate'::character varying, 'system'::character varying])::text[])))
);


--
-- Name: candidate_note_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.candidate_note_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: candidate_occupation; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.candidate_occupation (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    years_experience bigint,
    candidate_id bigint,
    occupation_id bigint
);


--
-- Name: candidate_occupation_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.candidate_occupation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: candidate_skill; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.candidate_skill (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    skill character varying(255),
    time_period character varying(255),
    candidate_id bigint
);


--
-- Name: candidate_skill_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.candidate_skill_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: candidate_visa_check; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.candidate_visa_check (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    character_assessment character varying(255),
    destination_family character varying(255),
    english_threshold character varying(255),
    health_assessment character varying(255),
    overall_risk character varying(255),
    pathway_assessment character varying(255),
    protection character varying(255),
    security_risk character varying(255),
    valid_travel_docs character varying(255),
    candidate_id bigint,
    country_id bigint,
    CONSTRAINT candidate_visa_check_character_assessment_check CHECK (((character_assessment)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_visa_check_destination_family_check CHECK (((destination_family)::text = ANY ((ARRAY['NoResponse'::character varying, 'NoRelation'::character varying, 'Child'::character varying, 'Parent'::character varying, 'Sibling'::character varying, 'AuntUncle'::character varying, 'Grandparent'::character varying, 'Cousin'::character varying, 'Other'::character varying])::text[]))),
    CONSTRAINT candidate_visa_check_english_threshold_check CHECK (((english_threshold)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_visa_check_health_assessment_check CHECK (((health_assessment)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_visa_check_overall_risk_check CHECK (((overall_risk)::text = ANY ((ARRAY['NoResponse'::character varying, 'Low'::character varying, 'Medium'::character varying, 'High'::character varying])::text[]))),
    CONSTRAINT candidate_visa_check_pathway_assessment_check CHECK (((pathway_assessment)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying, 'Unsure'::character varying])::text[]))),
    CONSTRAINT candidate_visa_check_protection_check CHECK (((protection)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_visa_check_security_risk_check CHECK (((security_risk)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_visa_check_valid_travel_docs_check CHECK (((valid_travel_docs)::text = ANY ((ARRAY['NoResponse'::character varying, 'Valid'::character varying, 'Expired'::character varying, 'None'::character varying])::text[])))
);


--
-- Name: candidate_visa_check_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.candidate_visa_check_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: candidate_visa_job_check; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.candidate_visa_job_check (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    age_requirement character varying(255),
    eligible_other character varying(255),
    eligible_186 character varying(255),
    eligible_494 character varying(255),
    interest character varying(255),
    languages_required character varying(255),
    languages_threshold_met character varying(255),
    put_forward character varying(255),
    qualification character varying(255),
    regional character varying(255),
    salary_tsmit character varying(255),
    tc_eligibility character varying(255),
    candidate_visa_check_id bigint,
    job_opp_id bigint,
    occupation_id bigint,
    CONSTRAINT candidate_visa_job_check_eligible_186_check CHECK (((eligible_186)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_visa_job_check_eligible_494_check CHECK (((eligible_494)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_visa_job_check_eligible_other_check CHECK (((eligible_other)::text = ANY ((ARRAY['NoResponse'::character varying, 'TempSkilled'::character varying, 'SpecialHum'::character varying, 'OtherHum'::character varying, 'DirectEnt'::character varying, 'PointsIndep'::character varying])::text[]))),
    CONSTRAINT candidate_visa_job_check_interest_check CHECK (((interest)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_visa_job_check_languages_threshold_met_check CHECK (((languages_threshold_met)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_visa_job_check_put_forward_check CHECK (((put_forward)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'YesBut'::character varying, 'DiscussFurther'::character varying, 'SeekAdvice'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_visa_job_check_qualification_check CHECK (((qualification)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_visa_job_check_regional_check CHECK (((regional)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_visa_job_check_salary_tsmit_check CHECK (((salary_tsmit)::text = ANY ((ARRAY['NoResponse'::character varying, 'Yes'::character varying, 'No'::character varying])::text[]))),
    CONSTRAINT candidate_visa_job_check_tc_eligibility_check CHECK (((tc_eligibility)::text = ANY ((ARRAY['NoResponse'::character varying, 'Proceed'::character varying, 'Discuss'::character varying, 'DontProceed'::character varying])::text[])))
);


--
-- Name: candidate_visa_job_check_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.candidate_visa_job_check_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: country; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.country (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    iso_code character varying(255),
    name character varying(255),
    status character varying(255),
    CONSTRAINT country_status_check CHECK (((status)::text = ANY ((ARRAY['active'::character varying, 'inactive'::character varying, 'deleted'::character varying])::text[])))
);


--
-- Name: country_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.country_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: education_level; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.education_level (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    education_type character varying(255),
    level integer NOT NULL,
    status character varying(255),
    CONSTRAINT education_level_education_type_check CHECK (((education_type)::text = ANY ((ARRAY['Associate'::character varying, 'Vocational'::character varying, 'Bachelor'::character varying, 'Masters'::character varying, 'Doctoral'::character varying])::text[]))),
    CONSTRAINT education_level_status_check CHECK (((status)::text = ANY ((ARRAY['active'::character varying, 'inactive'::character varying, 'deleted'::character varying])::text[])))
);


--
-- Name: education_level_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.education_level_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: education_major; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.education_major (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    isced_code character varying(255),
    name character varying(255),
    status character varying(255),
    CONSTRAINT education_major_status_check CHECK (((status)::text = ANY ((ARRAY['active'::character varying, 'inactive'::character varying, 'deleted'::character varying])::text[])))
);


--
-- Name: education_major_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.education_major_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: employer; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.employer (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    has_hired_internationally boolean,
    country_id bigint
);


--
-- Name: employer_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.employer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: language; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.language (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    iso_code character varying(255),
    name character varying(255),
    status character varying(255),
    CONSTRAINT language_status_check CHECK (((status)::text = ANY ((ARRAY['active'::character varying, 'inactive'::character varying, 'deleted'::character varying])::text[])))
);


--
-- Name: language_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.language_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: language_level; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.language_level (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    cefr_level character varying(255),
    level integer NOT NULL,
    name character varying(255),
    status character varying(255),
    CONSTRAINT language_level_cefr_level_check CHECK (((cefr_level)::text = ANY ((ARRAY['A1'::character varying, 'A2'::character varying, 'B1'::character varying, 'B2'::character varying, 'C1'::character varying, 'C2'::character varying])::text[]))),
    CONSTRAINT language_level_status_check CHECK (((status)::text = ANY ((ARRAY['active'::character varying, 'inactive'::character varying, 'deleted'::character varying])::text[])))
);


--
-- Name: occupation; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.occupation (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    isco08_code character varying(255),
    name character varying(255),
    status character varying(255),
    CONSTRAINT occupation_status_check CHECK (((status)::text = ANY ((ARRAY['active'::character varying, 'inactive'::character varying, 'deleted'::character varying])::text[])))
);


--
-- Name: occupation_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.occupation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: salesforce_job_opp; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.salesforce_job_opp (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    employer_hired_internationally boolean,
    evergreen boolean NOT NULL,
    hiring_commitment bigint,
    published_date timestamp(6) with time zone,
    stage character varying(255),
    submission_due_date date,
    country_object_id bigint,
    employer_id bigint,
    CONSTRAINT salesforce_job_opp_stage_check CHECK (((stage)::text = ANY ((ARRAY['prospect'::character varying, 'briefing'::character varying, 'pitching'::character varying, 'mou'::character varying, 'identifyingRoles'::character varying, 'candidateSearch'::character varying, 'visaEligibility'::character varying, 'cvPreparation'::character varying, 'cvReview'::character varying, 'recruitmentProcess'::character varying, 'jobOffer'::character varying, 'training'::character varying, 'visaPreparation'::character varying, 'postHireEngagement'::character varying, 'hiringCompleted'::character varying, 'ineligibleEmployer'::character varying, 'ineligibleOccupation'::character varying, 'ineligibleRegion'::character varying, 'noInterest'::character varying, 'noJobOffer'::character varying, 'noPrPathway'::character varying, 'noSuitableCandidates'::character varying, 'noVisa'::character varying, 'tooExpensive'::character varying, 'tooHighWage'::character varying, 'tooLong'::character varying, 'mouIssue'::character varying, 'trainingNotCompleted'::character varying])::text[])))
);


--
-- Name: salesforce_job_opp_tc_job_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.salesforce_job_opp_tc_job_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: survey_type; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.survey_type (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    updated_date timestamp(6) with time zone,
    name character varying(255),
    status character varying(255),
    CONSTRAINT survey_type_status_check CHECK (((status)::text = ANY ((ARRAY['active'::character varying, 'inactive'::character varying, 'deleted'::character varying])::text[])))
);


--
-- Name: survey_type_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.survey_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: batch_job_execution_context batch_job_execution_context_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.batch_job_execution_context
    ADD CONSTRAINT batch_job_execution_context_pkey PRIMARY KEY (job_execution_id);


--
-- Name: batch_job_execution batch_job_execution_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.batch_job_execution
    ADD CONSTRAINT batch_job_execution_pkey PRIMARY KEY (job_execution_id);


--
-- Name: batch_job_instance batch_job_instance_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.batch_job_instance
    ADD CONSTRAINT batch_job_instance_pkey PRIMARY KEY (job_instance_id);


--
-- Name: batch_step_execution_context batch_step_execution_context_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.batch_step_execution_context
    ADD CONSTRAINT batch_step_execution_context_pkey PRIMARY KEY (step_execution_id);


--
-- Name: batch_step_execution batch_step_execution_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.batch_step_execution
    ADD CONSTRAINT batch_step_execution_pkey PRIMARY KEY (step_execution_id);


--
-- Name: candidate_certification candidate_certification_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_certification
    ADD CONSTRAINT candidate_certification_pkey PRIMARY KEY (id);


--
-- Name: candidate_citizenship candidate_citizenship_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_citizenship
    ADD CONSTRAINT candidate_citizenship_pkey PRIMARY KEY (id);


--
-- Name: candidate_dependant candidate_dependant_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_dependant
    ADD CONSTRAINT candidate_dependant_pkey PRIMARY KEY (id);


--
-- Name: candidate_destination candidate_destination_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_destination
    ADD CONSTRAINT candidate_destination_pkey PRIMARY KEY (id);


--
-- Name: candidate_education candidate_education_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_education
    ADD CONSTRAINT candidate_education_pkey PRIMARY KEY (id);


--
-- Name: candidate_exam candidate_exam_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_exam
    ADD CONSTRAINT candidate_exam_pkey PRIMARY KEY (id);


--
-- Name: candidate_job_experience candidate_job_experience_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_job_experience
    ADD CONSTRAINT candidate_job_experience_pkey PRIMARY KEY (id);


--
-- Name: candidate_language candidate_language_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_language
    ADD CONSTRAINT candidate_language_pkey PRIMARY KEY (id);


--
-- Name: candidate_note candidate_note_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_note
    ADD CONSTRAINT candidate_note_pkey PRIMARY KEY (id);


--
-- Name: candidate_occupation candidate_occupation_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_occupation
    ADD CONSTRAINT candidate_occupation_pkey PRIMARY KEY (id);


--
-- Name: candidate candidate_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT candidate_pkey PRIMARY KEY (id);


--
-- Name: candidate_skill candidate_skill_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_skill
    ADD CONSTRAINT candidate_skill_pkey PRIMARY KEY (id);


--
-- Name: candidate_visa_check candidate_visa_check_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_visa_check
    ADD CONSTRAINT candidate_visa_check_pkey PRIMARY KEY (id);


--
-- Name: candidate_visa_job_check candidate_visa_job_check_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_visa_job_check
    ADD CONSTRAINT candidate_visa_job_check_pkey PRIMARY KEY (id);


--
-- Name: country country_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.country
    ADD CONSTRAINT country_pkey PRIMARY KEY (id);


--
-- Name: education_level education_level_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.education_level
    ADD CONSTRAINT education_level_pkey PRIMARY KEY (id);


--
-- Name: education_major education_major_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.education_major
    ADD CONSTRAINT education_major_pkey PRIMARY KEY (id);


--
-- Name: employer employer_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.employer
    ADD CONSTRAINT employer_pkey PRIMARY KEY (id);


--
-- Name: batch_job_instance job_inst_un; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.batch_job_instance
    ADD CONSTRAINT job_inst_un UNIQUE (job_name, job_key);


--
-- Name: language_level language_level_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.language_level
    ADD CONSTRAINT language_level_pkey PRIMARY KEY (id);


--
-- Name: language language_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.language
    ADD CONSTRAINT language_pkey PRIMARY KEY (id);


--
-- Name: occupation occupation_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.occupation
    ADD CONSTRAINT occupation_pkey PRIMARY KEY (id);


--
-- Name: salesforce_job_opp salesforce_job_opp_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.salesforce_job_opp
    ADD CONSTRAINT salesforce_job_opp_pkey PRIMARY KEY (id);


--
-- Name: survey_type survey_type_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.survey_type
    ADD CONSTRAINT survey_type_pkey PRIMARY KEY (id);


--
-- Name: candidate uk7qggcpt7sbrf34xyor5glqrh1; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT uk7qggcpt7sbrf34xyor5glqrh1 UNIQUE (migration_education_major_id);


--
-- Name: candidate ukcxvcktleljissg7xxojbbh7dr; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT ukcxvcktleljissg7xxojbbh7dr UNIQUE (public_id);


--
-- Name: candidate_visa_job_check fk129sotggwxdckvo2554m9i13q; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_visa_job_check
    ADD CONSTRAINT fk129sotggwxdckvo2554m9i13q FOREIGN KEY (occupation_id) REFERENCES public.occupation(id);


--
-- Name: candidate fk1ynj20ito5f3vdxn0db7e8rlc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT fk1ynj20ito5f3vdxn0db7e8rlc FOREIGN KEY (survey_type_id) REFERENCES public.survey_type(id);


--
-- Name: candidate_dependant fk5c9w8rkbyynkdugn6nvwhp9xl; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_dependant
    ADD CONSTRAINT fk5c9w8rkbyynkdugn6nvwhp9xl FOREIGN KEY (candidate_id) REFERENCES public.candidate(id);


--
-- Name: candidate_language fk5v3cotbd136it9i86onf8r7ac; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_language
    ADD CONSTRAINT fk5v3cotbd136it9i86onf8r7ac FOREIGN KEY (candidate_id) REFERENCES public.candidate(id);


--
-- Name: candidate_education fk8db2gg9h1dodcfj1mshp8vgv6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_education
    ADD CONSTRAINT fk8db2gg9h1dodcfj1mshp8vgv6 FOREIGN KEY (country_id) REFERENCES public.country(id);


--
-- Name: candidate_language fk90txfw6iv8umu8judh2g823y7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_language
    ADD CONSTRAINT fk90txfw6iv8umu8judh2g823y7 FOREIGN KEY (written_level_id) REFERENCES public.language_level(id);


--
-- Name: candidate_visa_job_check fka0ch7i1xd6ad7eyqpo0kqmbt8; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_visa_job_check
    ADD CONSTRAINT fka0ch7i1xd6ad7eyqpo0kqmbt8 FOREIGN KEY (candidate_visa_check_id) REFERENCES public.candidate_visa_check(id);


--
-- Name: candidate_visa_check fkardvhh4pmlf32baxh8xw34arg; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_visa_check
    ADD CONSTRAINT fkardvhh4pmlf32baxh8xw34arg FOREIGN KEY (country_id) REFERENCES public.country(id);


--
-- Name: candidate fkavc5dydajvmlcodnoltkde4ax; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT fkavc5dydajvmlcodnoltkde4ax FOREIGN KEY (nationality_id) REFERENCES public.country(id);


--
-- Name: candidate_destination fkbdtwtvnlkwygispg0hc1hfqto; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_destination
    ADD CONSTRAINT fkbdtwtvnlkwygispg0hc1hfqto FOREIGN KEY (candidate_id) REFERENCES public.candidate(id);


--
-- Name: candidate_education fkc9vk3y48f588q95r18bf9tl18; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_education
    ADD CONSTRAINT fkc9vk3y48f588q95r18bf9tl18 FOREIGN KEY (major_id) REFERENCES public.education_major(id);


--
-- Name: candidate_occupation fkcqw5ppqt7so8itofrompnmgo4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_occupation
    ADD CONSTRAINT fkcqw5ppqt7so8itofrompnmgo4 FOREIGN KEY (candidate_id) REFERENCES public.candidate(id);


--
-- Name: candidate fkct5r6y0jq6fxyd56l21dtjmio; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT fkct5r6y0jq6fxyd56l21dtjmio FOREIGN KEY (partner_edu_level_id) REFERENCES public.education_level(id);


--
-- Name: candidate fkfjukssrlg16860w4lyfqyj2vp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT fkfjukssrlg16860w4lyfqyj2vp FOREIGN KEY (driving_license_country_id) REFERENCES public.country(id);


--
-- Name: candidate_job_experience fkgrjtieyb1v277nn9emwvmw1ba; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_job_experience
    ADD CONSTRAINT fkgrjtieyb1v277nn9emwvmw1ba FOREIGN KEY (country_id) REFERENCES public.country(id);


--
-- Name: candidate fkhnekvqcv5ot86if4q7b64bnjy; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT fkhnekvqcv5ot86if4q7b64bnjy FOREIGN KEY (max_education_level_id) REFERENCES public.education_level(id);


--
-- Name: candidate_education fkhw87pfd8o09yvwivyfe3r5vep; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_education
    ADD CONSTRAINT fkhw87pfd8o09yvwivyfe3r5vep FOREIGN KEY (candidate_id) REFERENCES public.candidate(id);


--
-- Name: candidate_certification fki2bo9sk8but4ls2ov3gu6rsti; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_certification
    ADD CONSTRAINT fki2bo9sk8but4ls2ov3gu6rsti FOREIGN KEY (candidate_id) REFERENCES public.candidate(id);


--
-- Name: employer fkiikqegug79ostlf056akqpoos; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.employer
    ADD CONSTRAINT fkiikqegug79ostlf056akqpoos FOREIGN KEY (country_id) REFERENCES public.country(id);


--
-- Name: candidate_skill fkijjf42p0sh2c2na28g5aalx2p; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_skill
    ADD CONSTRAINT fkijjf42p0sh2c2na28g5aalx2p FOREIGN KEY (candidate_id) REFERENCES public.candidate(id);


--
-- Name: candidate_job_experience fkir0a73p14uo6c745cy2whkplq; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_job_experience
    ADD CONSTRAINT fkir0a73p14uo6c745cy2whkplq FOREIGN KEY (candidate_occupation_id) REFERENCES public.candidate_occupation(id);


--
-- Name: candidate fkjwdrin1dvu8x6evxjfi9ojx71; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT fkjwdrin1dvu8x6evxjfi9ojx71 FOREIGN KEY (migration_education_major_id) REFERENCES public.education_major(id);


--
-- Name: candidate_language fkjx1fgxxw4el3fnb13xk79ady1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_language
    ADD CONSTRAINT fkjx1fgxxw4el3fnb13xk79ady1 FOREIGN KEY (language_id) REFERENCES public.language(id);


--
-- Name: candidate_citizenship fkk3dhqdnpg4yog6mub6qhnv5ix; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_citizenship
    ADD CONSTRAINT fkk3dhqdnpg4yog6mub6qhnv5ix FOREIGN KEY (nationality_id) REFERENCES public.country(id);


--
-- Name: candidate_visa_job_check fkk3feqw6x02m5mlswep748k3gr; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_visa_job_check
    ADD CONSTRAINT fkk3feqw6x02m5mlswep748k3gr FOREIGN KEY (job_opp_id) REFERENCES public.salesforce_job_opp(id);


--
-- Name: candidate_occupation fkkbqvqc7est2tdvgh0tl66nwyn; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_occupation
    ADD CONSTRAINT fkkbqvqc7est2tdvgh0tl66nwyn FOREIGN KEY (occupation_id) REFERENCES public.occupation(id);


--
-- Name: salesforce_job_opp fkko155b2fr691nu54uykfmgjsq; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.salesforce_job_opp
    ADD CONSTRAINT fkko155b2fr691nu54uykfmgjsq FOREIGN KEY (employer_id) REFERENCES public.employer(id);


--
-- Name: candidate_job_experience fkkqct81jqqj4qgfidck92jcsnk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_job_experience
    ADD CONSTRAINT fkkqct81jqqj4qgfidck92jcsnk FOREIGN KEY (candidate_id) REFERENCES public.candidate(id);


--
-- Name: candidate_visa_check fklafykldkr0uhy51wyrmoktllm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_visa_check
    ADD CONSTRAINT fklafykldkr0uhy51wyrmoktllm FOREIGN KEY (candidate_id) REFERENCES public.candidate(id);


--
-- Name: candidate_exam fklg4gauadxp6jd5inhk2ry9yrs; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_exam
    ADD CONSTRAINT fklg4gauadxp6jd5inhk2ry9yrs FOREIGN KEY (candidate_id) REFERENCES public.candidate(id);


--
-- Name: candidate fko342h9ey824s0scxhcxm0dwnc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT fko342h9ey824s0scxhcxm0dwnc FOREIGN KEY (partner_english_level_id) REFERENCES public.language_level(id);


--
-- Name: candidate_language fkoaq3pqw64bokgcesf6pq0mvm0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_language
    ADD CONSTRAINT fkoaq3pqw64bokgcesf6pq0mvm0 FOREIGN KEY (spoken_level_id) REFERENCES public.language_level(id);


--
-- Name: candidate_citizenship fkovkfrsv3e4uc0f4yctmc99gyu; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_citizenship
    ADD CONSTRAINT fkovkfrsv3e4uc0f4yctmc99gyu FOREIGN KEY (candidate_id) REFERENCES public.candidate(id);


--
-- Name: candidate fkpb3vpin95ykh1tyty0y0uhrpc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT fkpb3vpin95ykh1tyty0y0uhrpc FOREIGN KEY (country_id) REFERENCES public.country(id);


--
-- Name: candidate fkpjhpuvifet1k2gxumcjy6xss3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT fkpjhpuvifet1k2gxumcjy6xss3 FOREIGN KEY (birth_country_id) REFERENCES public.country(id);


--
-- Name: candidate_destination fkqal70kcgl7e0v7iq14wupaljs; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_destination
    ADD CONSTRAINT fkqal70kcgl7e0v7iq14wupaljs FOREIGN KEY (country_id) REFERENCES public.country(id);


--
-- Name: candidate fkqpxn8qv2o6fmpasw0pg9ugsx9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT fkqpxn8qv2o6fmpasw0pg9ugsx9 FOREIGN KEY (partner_occupation_id) REFERENCES public.occupation(id);


--
-- Name: salesforce_job_opp fkryw5lr65gyb77smartdx287bo; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.salesforce_job_opp
    ADD CONSTRAINT fkryw5lr65gyb77smartdx287bo FOREIGN KEY (country_object_id) REFERENCES public.country(id);


--
-- Name: candidate_note fkt732aiwddb2m1cac4fq6d476h; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.candidate_note
    ADD CONSTRAINT fkt732aiwddb2m1cac4fq6d476h FOREIGN KEY (candidate_id) REFERENCES public.candidate(id);


--
-- Name: batch_job_execution_context job_exec_ctx_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.batch_job_execution_context
    ADD CONSTRAINT job_exec_ctx_fk FOREIGN KEY (job_execution_id) REFERENCES public.batch_job_execution(job_execution_id);


--
-- Name: batch_job_execution_params job_exec_params_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.batch_job_execution_params
    ADD CONSTRAINT job_exec_params_fk FOREIGN KEY (job_execution_id) REFERENCES public.batch_job_execution(job_execution_id);


--
-- Name: batch_step_execution job_exec_step_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.batch_step_execution
    ADD CONSTRAINT job_exec_step_fk FOREIGN KEY (job_execution_id) REFERENCES public.batch_job_execution(job_execution_id);


--
-- Name: batch_job_execution job_inst_exec_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.batch_job_execution
    ADD CONSTRAINT job_inst_exec_fk FOREIGN KEY (job_instance_id) REFERENCES public.batch_job_instance(job_instance_id);


--
-- Name: batch_step_execution_context step_exec_ctx_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.batch_step_execution_context
    ADD CONSTRAINT step_exec_ctx_fk FOREIGN KEY (step_execution_id) REFERENCES public.batch_step_execution(step_execution_id);


--
-- PostgreSQL database dump complete
--

