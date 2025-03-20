module tc-test {
  source = "./.."
  project_name = "tc-api"
  project_description = "Staging setup for tc-api"
  image_tag = "0.0.1-SNAPSHOT"
  fargate_cpu = 512
  fargate_memory = 2048
  db_name = "tcapi"
  db_user_name = "tcapi"
  db_instance_class = "db.t3.medium" # smallest test instance available for aurora
  doc_db_name = "tcapi"
  doc_db_user_name = "tcapi"
  doc_db_service_name = "mongo"
  dns_namespace = "tc-api.local"
  app_port = 8082
}
