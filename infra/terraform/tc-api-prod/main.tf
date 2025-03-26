module tc-prod {
  source = "./.."
  project_name = "tc-api"
  project_description = "Production setup for tc-api"
  image_tag = "1.0.0"
  fargate_cpu = 512
  fargate_memory = 2048
  db_name = "tcapi"
  db_user_name = "tcapi"
  db_instance_class = "db.t3.medium" # 2vCPU, 4GB RAM
  doc_db_name = "tcapi"
  doc_db_user_name = "tcapi"
  doc_db_service_name = "mongo"
  dns_namespace = "tc-api.local"
  app_port = 8082
  health_check_path = "/actuator/health"
  tc_api_url = "https://tctalent.org/api/admin"
  tc_api_search_id = 3434
  tc_api_username = "tc-api"
  run_anonymisation_on_startup = false
}
