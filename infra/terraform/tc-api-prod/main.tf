# Secrets loaded from secrets.auto.tfvars and passed through to the child module
variable "database_password" {
  description = "RDS Aurora master password"
  type        = string
  sensitive   = true
  default     = ""
}

variable "doc_db_password" {
  description = "MongoDB Atlas password"
  type        = string
  sensitive   = true
  default     = ""
}

variable "tc_password" {
  description = "Talent Catalog API password"
  type        = string
  sensitive   = true
  default     = ""
}

provider "aws" {
  region = "us-east-1"
}

module tc-prod {
  source = "./.."

  # Terraform inputs
  project_name        = "tc-api"
  project_description = "Production setup for tc-api"
  environment         = "prod"
  image_tag           = "1.0.1-SNAPSHOT"
  fargate_cpu         = 512
  fargate_memory      = 2048
  app_port            = 8082
  health_check_path   = "/actuator/health"
  db_name             = "tcapi"
  db_user_name        = "tcapi"
  db_instance_class   = "db.t3.medium" # 2vCPU, 4GB RAM
  db_version          = "17.7"
  dns_namespace       = "tc-api.local"
  site_domain              = "api.tctalent.org"
  manage_public_dns        = false
  manage_ssm_parameters    = false
  create_database_internet_gateway_route = true
  manage_master_user_password = false
  existing_certificate_arn = "arn:aws:acm:us-east-1:968457613372:certificate/5dd8d298-5460-4396-ad5e-24e3a2dfa774"
  vanta_user_data_stored   = "Pseudonymised candidate data"

  # SSM-backed
  tc_api_url                   = "https://tctalent.org/api/admin"
  tc_search_id             = 3434
  tc_username              = "tc-api"
  tc_password              = var.tc_password
  database_password        = var.database_password
  doc_db_cluster_name      = "prod.t9anmq2.mongodb.net"
  doc_db_name              = "tcapi"
  doc_db_user_name         = "tcapi"
  doc_db_password          = var.doc_db_password
  batch_chunk_size         = "20"
  batch_page_size          = "20"
  batch_max_read_skips     = "10"
  batch_fetch_delay_millis = "1000"
}

terraform {
  backend "s3" {
    bucket         = "tc-api-terraform-state-prod"
    key            = "state/terraform.tfstate"
    region         = "us-east-1"
    encrypt        = true
  }
}
