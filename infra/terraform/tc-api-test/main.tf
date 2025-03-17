module tc-test {
  source = "./.."
  project_name = "tc-api"
  project_description = "Staging setup for tc-api"
  fargate_cpu = 512
  fargate_memory = 2048
  db_name = "tcapi"
  db_user_name = "tcapi"
  db_instance_class = "db.t3.medium" # smallest test instance available for aurora
}
