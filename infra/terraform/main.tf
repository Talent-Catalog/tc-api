# Use standard Terraform AWS modules where possible.
# See https://registry.terraform.io/browse/modules?provider=aws

# todo Use secrets manager

provider "aws" {
  region = local.region
}

data "aws_availability_zones" "available" {}

locals {
  name    = var.project_name
  description = var.project_description
  region  = var.aws_region

  # This forms the base of our network addresses: the first 16 bits (the 10.0) will be unchanged.
  vpc_cidr = "10.0.0.0/16"

  #This selects three of the AWS existing availability zones
  azs = slice(data.aws_availability_zones.available.names, 0, 3)

  container_name = "${local.name}-container"

  container_port = var.app_port

  tags = {
    Name       = local.name
    Repository = "https://github.com/Talent-Catalog/tc-api"
  }
}

################################################################################
# Cluster
################################################################################

module "ecs_cluster" {
  source  = "terraform-aws-modules/ecs/aws//modules/cluster"

  cluster_name = local.name

  # Capacity provider
  fargate_capacity_providers = {
    FARGATE = {
      default_capacity_provider_strategy = {
        weight = 50
        base   = 20
      }
    }
    FARGATE_SPOT = {
      default_capacity_provider_strategy = {
        weight = 50
      }
    }
  }

  tags = local.tags
}

################################################################################
# TC API Service
################################################################################

module "ecs_service" {
  source = "terraform-aws-modules/ecs/aws//modules/service"
  depends_on = [module.db]

  name        = local.name
  cluster_arn = module.ecs_cluster.arn

  cpu    = var.fargate_cpu
  memory = var.fargate_memory

  # Enables ECS Exec
  enable_execute_command = true

  # Container definition(s)
  container_definitions = {

    (local.container_name) = {
      cpu       = var.fargate_cpu
      memory    = var.fargate_memory
      essential = true

      image = "${aws_ecr_repository.repo.repository_url}:${var.image_tag}"
      port_mappings = [
        {
          name          = local.container_name
          containerPort = local.container_port
          hostPort      = local.container_port
          protocol      = "tcp"
        }
      ]

      environment = [
        {
          name  = "DATABASE_URL"
          value = "jdbc:postgresql://${module.db.cluster_endpoint}/${var.db_name}"
        },
        {
          name  = "DATABASE_USERNAME"
          value = var.db_user_name
        },
        {
          name  = "DATABASE_PASSWORD"
          value = ""
        },
        {
          name  = "MONGO_URL"
          value = format(
            "mongodb://%s:tctalent@%s.%s:27017/%s?authSource=admin&tls=false&directConnection=true",
            var.doc_db_user_name,
            var.doc_db_service_name,
            var.dns_namespace,
            var.doc_db_name,
          )
        },
        {
          name  = "TC_API_URL"
          value = var.tc_api_url
        },
        {
          name  = "TC_SEARCH_ID"
          value = var.tc_api_search_id
        },
        {
          name  = "TC_USERNAME"
          value = var.tc_api_username
        },
        {
          name  = "TC_PASSWORD"
          value = ""
        },
        {
          name  = "SPRING_BATCH_JOB_ENABLED"
          value = var.run_anonymisation_on_startup
        },

      ]

      # Example image used requires access to write to root filesystem
      readonly_root_filesystem = false

      enable_cloudwatch_logging = true
      log_configuration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = "/fargate/service/${local.name}-fargate-log"
          awslogs-stream-prefix = "ecs"
          awslogs-region        = local.region
        }
      }

      linux_parameters = {
        capabilities = {
          add = []
          drop = [
            "NET_RAW"
          ]
        }
      }

      memory_reservation = 100
    }
  }

  service_connect_configuration = {
    namespace = aws_service_discovery_private_dns_namespace.this.arn
    services = [{
      discovery_name = local.container_name
      port_name      = local.container_name
      client_aliases = []
    }]

    client_services = [{
      discovery_name = "mongo"
      port           = 27017
      dns_name       = "mongo"
    }]
  }

  load_balancer = {
    service = {
      target_group_arn = module.alb.target_groups["ex_ecs"].arn
      container_name   = local.container_name
      container_port   = local.container_port
    }
  }

  subnet_ids = module.vpc.private_subnets
  security_group_rules = {
    alb_ingress_3000 = {
      type                     = "ingress"
      from_port                = local.container_port
      to_port                  = local.container_port
      protocol                 = "tcp"
      description              = "Service port"
      source_security_group_id = module.alb.security_group_id
    }
    egress_all = {
      type        = "egress"
      from_port   = 0
      to_port     = 0
      protocol    = "-1"
      cidr_blocks = ["0.0.0.0/0"]
    }
  }

  service_tags = {
    "ServiceTag" = "Tag on service level"
  }

  tags = local.tags
}

################################################################################
# Mongo DB Service
################################################################################

module "mongo_service" {
  source      = "terraform-aws-modules/ecs/aws//modules/service"
  name        = "${local.name}-mongo"
  cluster_arn = module.ecs_cluster.arn

  cpu           = 256
  memory        = 512
  desired_count = 1
  launch_type   = "FARGATE"

  # Enables ECS Exec
  enable_execute_command = true
  platform_version       = "LATEST"

  volume = {
    "${local.name}-mongo-efs" = {
      name = "${local.name}-mongo-efs"
      efs_volume_configuration = {
        file_system_id     = aws_efs_file_system.mongo.id
        transit_encryption = "ENABLED"
        authorization_config = {
          iam = "ENABLED"
        }
        # Optionally, add root_directory if needed:
        # root_directory = "/"
      }
    }
  }

  container_definitions = {
    mongo = {
      image         = "mongo:8.0"
      cpu           = 256
      memory        = 512
      essential     = true

      port_mappings = [
        {
          name          = "mongo"
          containerPort = 27017,
          hostPort      = 27017
          protocol      = "tcp"
        }
      ]
      environment   = [
        {
          name = "MONGO_INITDB_ROOT_USERNAME",
          value = var.doc_db_user_name
        },
        {
          name = "MONGO_INITDB_ROOT_PASSWORD",
          value = "tctalent"
        },
        {
          name = "MONGO_INITDB_DATABASE",
          value = var.doc_db_name
        },
      ]

      # Mount the EFS volume at /data/db
      mount_points = [
        {
          sourceVolume  = "${local.name}-mongo-efs"
          containerPath = "/data/db"
          readOnly      = false
        }
      ]

      readonly_root_filesystem = false

      enable_cloudwatch_logging = true
      log_configuration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = "/fargate/service/${local.name}-mongo-log"
          awslogs-stream-prefix = "mongodb"
          awslogs-region        = local.region
        }
      }

      linux_parameters = {
        capabilities = {
          add = []
          drop = [ "NET_RAW" ]
        }
      }

      memory_reservation = 100
    }
  }

  subnet_ids = module.vpc.private_subnets

  security_group_rules = {
    mongo_ingress = {
      type                     = "ingress"
      from_port                = 27017
      to_port                  = 27017
      protocol                 = "tcp"
      source_security_group_id = module.ecs_service.security_group_id
    }
    efs_ingress = {
      type                     = "ingress"
      from_port                = 2049
      to_port                  = 2049
      protocol                 = "tcp"
      source_security_group_id = module.mongo_service.security_group_id
      description              = "Allow NFS traffic for EFS"
    }
    egress_all = {
      type        = "egress"
      from_port   = 0
      to_port     = 0
      protocol    = "-1"
      cidr_blocks = ["0.0.0.0/0"]
    }
  }

  service_connect_configuration = {
    namespace = aws_service_discovery_private_dns_namespace.this.arn
    services = [{
      discovery_name = "mongo"
      port_name      = "mongo"
      client_aliases = [{
        dns_name = "mongo"
        port     = 27017
      }]
    }]
  }

  service_registries = {
    registry_arn = aws_service_discovery_service.mongo.arn
  }

  tags = local.tags
}

################################################################################
# EFS for Persistence
################################################################################

resource "aws_efs_file_system" "mongo" {
  creation_token = "${local.name}-mongo-efs"
  performance_mode = "generalPurpose"
  encrypted = false

  tags = merge(local.tags, { Name = "${local.name}-mongo-efs" })
}

resource "aws_efs_mount_target" "mongo" {
  for_each = { for idx, subnet in module.vpc.private_subnets : idx => subnet }

  file_system_id  = aws_efs_file_system.mongo.id
  subnet_id       = each.value
  security_groups = [module.mongo_service.security_group_id]
}

################################################################################
# RDS Module
################################################################################
module "db" {
  # See https://registry.terraform.io/modules/terraform-aws-modules/rds-aurora/aws/latest
  source  = "terraform-aws-modules/rds-aurora/aws"
  version = "9.13.0"

  name           = local.name

  engine         = "aurora-postgresql"
  engine_version = "15"
  instance_class = var.db_instance_class
  instances = {
    one = {}
  }

  publicly_accessible = true

  master_username = var.db_user_name
  database_name = var.db_name

  vpc_id               = module.vpc.vpc_id
  db_subnet_group_name = module.vpc.database_subnet_group
  security_group_rules = {
    allow_local_access = {
      type                     = "ingress"
      from_port                = 5432
      to_port                  = 5432
      protocol                 = "tcp"
      description = "Allow local access to Aurora"
      cidr_blocks = ["0.0.0.0/0"]
    },
    egress_all = {
      type        = "egress"
      from_port   = 0
      to_port     = 0
      protocol    = "-1"
      cidr_blocks = ["0.0.0.0/0"]
    }
  }

  vpc_security_group_ids = [module.security_group.security_group_id]

  create_cloudwatch_log_group     = true

  backup_retention_period = 1
  skip_final_snapshot     = true
  deletion_protection     = false

  performance_insights_enabled          = true
  performance_insights_retention_period = 7
  create_monitoring_role                = true
  monitoring_interval                   = 60

  storage_encrypted   = true
  apply_immediately   = true

  enabled_cloudwatch_logs_exports = ["postgresql"]

  tags = local.tags
}

################################################################################
# Supporting Resources
################################################################################

module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "~> 5.0"

  name = local.name
  cidr = local.vpc_cidr

  azs              = local.azs

  # We have three types of subnet: public, private and database.
  # Here is a nice image illustrating those zones: https://miro.medium.com/v2/1*rH2xDaYPE_VOAT8vBKVTug.png
  # We need one of each of those types of subnet in each of the three availability zones
  # cidrsubnet is a standard function which calculates subnets: https://developer.hashicorp.com/terraform/language/functions/cidrsubnet
  public_subnets   = [for k, v in local.azs : cidrsubnet(local.vpc_cidr, 8, k)]
  private_subnets  = [for k, v in local.azs : cidrsubnet(local.vpc_cidr, 8, k + 3)]
  database_subnets = [for k, v in local.azs : cidrsubnet(local.vpc_cidr, 8, k + 6)]

  create_database_subnet_group = true
  create_database_subnet_route_table = true
  create_database_internet_gateway_route = true

  enable_dns_support   = true
  enable_dns_hostnames = true

  enable_nat_gateway = true
  single_nat_gateway = true

  tags = local.tags
}

module "security_group" {
  source  = "terraform-aws-modules/security-group/aws"
  version = "~> 5.0"

  name        = local.name
  description = "Talent Catalog M&E security group"
  vpc_id      = module.vpc.vpc_id

  # ingress
  ingress_with_cidr_blocks = [
    {
      from_port   = 5432
      to_port     = 5432
      protocol    = "tcp"
      description = "PostgreSQL access from within VPC"
      cidr_blocks = "0.0.0.0/0"
    },
  ]

  tags = local.tags
}

resource "aws_ecr_repository" "repo" {
  name                 = local.name
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

resource "aws_service_discovery_http_namespace" "this" {
  name        = local.name
  description = "CloudMap namespace for ${local.name}"
  tags        = local.tags
}

resource "aws_service_discovery_private_dns_namespace" "this" {
  name = var.dns_namespace
  description = "Private DNS namespace for ${var.dns_namespace}"
  vpc  = module.vpc.vpc_id
}

resource "aws_service_discovery_service" "mongo" {
  name         = var.doc_db_service_name
  namespace_id = aws_service_discovery_private_dns_namespace.this.id
  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.this.id
    dns_records {
      type = "A"
      ttl  = 10
    }
    routing_policy = "MULTIVALUE"
  }
}

module "alb" {
  source  = "terraform-aws-modules/alb/aws"
  version = "~> 9.0"

  name = local.name

  load_balancer_type = "application"

  vpc_id  = module.vpc.vpc_id
  subnets = module.vpc.public_subnets

  # For example only
  enable_deletion_protection = false

  # Security Group
  security_group_ingress_rules = {
    all_http = {
      from_port   = 80
      to_port     = 80
      ip_protocol = "tcp"
      cidr_ipv4   = "0.0.0.0/0"
    }
  }
  security_group_egress_rules = {
    all = {
      ip_protocol = "-1"
      cidr_ipv4   = module.vpc.vpc_cidr_block
    }
  }

  listeners = {
    ex_http = {
      port     = 80
      protocol = "HTTP"

      forward = {
        target_group_key = "ex_ecs"
      }
    }
  }

  target_groups = {
    ex_ecs = {
      backend_protocol                  = "HTTP"
      backend_port                      = local.container_port
      target_type                       = "ip"
      deregistration_delay              = 5
      load_balancing_cross_zone_enabled = true

      health_check = {
        enabled             = true
        healthy_threshold   = 2
        interval            = 60
        matcher             = "200"
        path                = var.health_check_path
        port                = "traffic-port"
        protocol            = "HTTP"
        timeout             = 10
        unhealthy_threshold = 5
      }

      # There's nothing to attach here in this definition. Instead,
      # ECS will attach the IPs of the tasks to this target group
      create_attachment = false
    }
  }

  tags = local.tags
}
