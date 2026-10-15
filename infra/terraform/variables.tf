# -----------------------------------------------------------------------------
# Kept as Terraform module inputs (infra / config)
# -----------------------------------------------------------------------------

variable "project_name" {
  description = "Name of project - all resources will be named based on this"
}

variable "project_description" {
  description = "Description of project"
}

variable "environment" {
  description = "Environment name used in SSM parameter paths (e.g. opc-staging, test, prod)"
  default     = "staging"
}

variable "aws_region" {
  description = "The AWS region things are created in"
  default     = "us-east-1"
}

variable "image_tag" {
  description = "The image tag for the ECS service"
  default     = "latest"
}

variable "app_port" {
  description = "Port exposed by the docker image to redirect traffic to"
  default     = 8088
}

variable "health_check_path" {
  default = "/"
}

variable "fargate_cpu" {
  description = "Fargate instance CPU units to provision (1 vCPU = 1024 CPU units)"
  default     = "1024"
}

variable "fargate_memory" {
  description = "Fargate instance memory to provision (in MiB)"
  default     = "4096"
}

variable "db_name" {
  description = "Name of the database"
}

variable "db_user_name" {
  description = "Database user name"
}

variable "db_version" {
  description = "Version of the database engine"
}

variable "db_instance_class" {
  description = "Instance class of database - see https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/Concepts.DBInstanceClass.Summary.html"
  default     = "db.t3.micro"
}

variable "dns_namespace" {
  description = "Private DNS namespace"
}

variable "site_domain" {
  description = "The domain name for the ACM certificate (e.g. skills.staging.example.org)"
}

variable "manage_public_dns" {
  description = "Create public Route53 + ACM resources for site_domain; disable when DNS is owned by another account."
  type        = bool
  default     = true
}

variable "existing_certificate_arn" {
  description = "ACM certificate ARN to attach to the ALB when manage_public_dns is false."
  type        = string
  default     = ""

  validation {
    condition     = var.manage_public_dns || var.existing_certificate_arn != ""
    error_message = "existing_certificate_arn is required when manage_public_dns is false."
  }
}

variable "manage_ssm_parameters" {
  description = "Create SSM parameters and inject them as ECS secrets. Disable to keep the existing inline-env task definition."
  type        = bool
  default     = true
}

variable "create_database_internet_gateway_route" {
  description = "Create a 0.0.0.0/0 route from the database route table to the VPC internet gateway."
  type        = bool
  default     = false
}

variable "manage_master_user_password" {
  description = "Let RDS manage the master password in Secrets Manager. When true, database_password is not sent to RDS (still used for ECS/SSM). When false, database_password is the static master password."
  type        = bool
  default     = false
}

variable "vanta_user_data_stored" {
  description = "Vanta data-classification tag (VantaUserDataStored) describing what user data this environment's database holds."
  type        = string
}

# -----------------------------------------------------------------------------
# SSM-backed config and secrets (stored in SSM, injected into ECS task)
# -----------------------------------------------------------------------------

variable "tc_api_url" {
  description = "Talent Catalog core service URL (TC_API_URL in SSM)"
}

variable "tc_search_id" {
  description = "Talent Catalog search id (TC_SEARCH_ID in SSM)"
}

variable "tc_username" {
  description = "Talent Catalog username (TC_USERNAME in SSM)"
}

variable "tc_password" {
  description = "Talent Catalog password (TC_PASSWORD in SSM as SecureString)"
  type        = string
  sensitive   = true
  default     = ""
}

variable "database_password" {
  description = "RDS Aurora master password (DATABASE_PASSWORD in SSM as SecureString, used by RDS and ECS)"
  type        = string
  sensitive   = true
  default     = ""
}

variable "doc_db_cluster_name" {
  description = "MongoDB Atlas cluster hostname (used to build MONGO_URL in SSM)"
}

variable "doc_db_name" {
  description = "MongoDB database name (used to build MONGO_URL in SSM)"
}

variable "doc_db_user_name" {
  description = "MongoDB user name (used to build MONGO_URL in SSM)"
}

variable "doc_db_password" {
  description = "MongoDB Atlas password (stored in SSM SecureString, used to build MONGO_URL)"
  type        = string
  sensitive   = true
  default     = ""
}

variable "batch_chunk_size" {
  description = "Batch chunk size (BATCH_CHUNK_SIZE in SSM)"
  type        = string
}

variable "batch_page_size" {
  description = "Batch page size (BATCH_PAGE_SIZE in SSM)"
  type        = string
}

variable "batch_max_read_skips" {
  description = "Max read skips for batch jobs (BATCH_MAX_READ_SKIPS in SSM)"
  type        = string
}

variable "batch_fetch_delay_millis" {
  description = "Fetch delay in ms for batch jobs (BATCH_FETCH_DELAY_MILLIS in SSM)"
  type        = string
}
