################################################################################
# CloudWatch alarms: ALB and Aurora RDS (TC-1526 - Vanta alarm coverage)
#
# All alarms are gated on var.cloudwatch_alarms_enabled (never on the SNS
# topic ARN directly - gating on an ARN that is unknown at plan time causes
# "Invalid count argument" errors).
################################################################################

locals {
  # Total memory (bytes) per RDS instance class, used to derive the freeable
  # memory alarm threshold as a percentage of instance memory. Covers the
  # instance classes used by the in-scope roots (tc-api-opc-prod,
  # tc-api-opc-test, tc-api-prod all use db.t3.medium).
  rds_instance_class_memory_bytes = {
    "db.t3.medium" = 4294967296 # 2 vCPU / 4 GiB
  }

  rds_instance_memory_bytes = (
    var.alarm_rds_instance_memory_bytes_override != null
    ? var.alarm_rds_instance_memory_bytes_override
    : lookup(local.rds_instance_class_memory_bytes, var.db_instance_class, null)
  )

  rds_freeable_memory_threshold_bytes = (
    local.rds_instance_memory_bytes != null
    ? floor(local.rds_instance_memory_bytes * var.alarm_rds_freeable_memory_threshold_percent / 100)
    : null
  )
}

################################################################################
# ALB alarms
################################################################################

resource "aws_cloudwatch_metric_alarm" "alb_unhealthy_hosts" {
  count = var.cloudwatch_alarms_enabled ? 1 : 0

  alarm_name          = "${local.name}-${var.environment}-alb-unhealthy-hosts"
  alarm_description   = "ALB ${local.name} (${var.environment}) target group has unhealthy hosts. TC-1526."
  namespace           = "AWS/ApplicationELB"
  metric_name         = "UnHealthyHostCount"
  statistic           = "Maximum"
  comparison_operator = "GreaterThanThreshold"
  threshold           = var.alarm_alb_unhealthy_host_count_threshold
  period              = 60
  evaluation_periods  = 5
  datapoints_to_alarm = 3
  treat_missing_data  = "notBreaching"

  dimensions = {
    TargetGroup  = module.alb.target_groups["ex_ecs"].arn_suffix
    LoadBalancer = module.alb.arn_suffix
  }

  alarm_actions = [var.alarm_sns_topic_arn]
  ok_actions    = [var.alarm_sns_topic_arn]

  tags = merge(local.tags, { Name = "${local.name}-${var.environment}-alb-unhealthy-hosts" })

  lifecycle {
    precondition {
      condition     = var.alarm_sns_topic_arn != null
      error_message = "alarm_sns_topic_arn must be set when cloudwatch_alarms_enabled is true (TC-1526)."
    }
  }
}

resource "aws_cloudwatch_metric_alarm" "alb_latency_p95" {
  count = var.cloudwatch_alarms_enabled ? 1 : 0

  alarm_name          = "${local.name}-${var.environment}-alb-latency-p95"
  alarm_description   = "ALB ${local.name} (${var.environment}) p95 target response time is elevated. TC-1526."
  namespace           = "AWS/ApplicationELB"
  metric_name         = "TargetResponseTime"
  extended_statistic  = "p95"
  comparison_operator = "GreaterThanThreshold"
  threshold           = var.alarm_alb_latency_p95_threshold_seconds
  period              = 60
  evaluation_periods  = 5
  datapoints_to_alarm = 5
  treat_missing_data  = "notBreaching"

  dimensions = {
    LoadBalancer = module.alb.arn_suffix
  }

  alarm_actions = [var.alarm_sns_topic_arn]
  ok_actions    = [var.alarm_sns_topic_arn]

  tags = merge(local.tags, { Name = "${local.name}-${var.environment}-alb-latency-p95" })
}

resource "aws_cloudwatch_metric_alarm" "alb_5xx_target" {
  count = var.cloudwatch_alarms_enabled ? 1 : 0

  alarm_name          = "${local.name}-${var.environment}-alb-5xx-target"
  alarm_description   = "ALB ${local.name} (${var.environment}) target is returning 5XX responses. TC-1526."
  namespace           = "AWS/ApplicationELB"
  metric_name         = "HTTPCode_Target_5XX_Count"
  statistic           = "Sum"
  comparison_operator = "GreaterThanThreshold"
  threshold           = var.alarm_alb_target_5xx_threshold
  period              = 300
  evaluation_periods  = 1
  datapoints_to_alarm = 1
  treat_missing_data  = "notBreaching"

  dimensions = {
    LoadBalancer = module.alb.arn_suffix
  }

  alarm_actions = [var.alarm_sns_topic_arn]
  ok_actions    = [var.alarm_sns_topic_arn]

  tags = merge(local.tags, { Name = "${local.name}-${var.environment}-alb-5xx-target" })
}

resource "aws_cloudwatch_metric_alarm" "alb_5xx_elb" {
  count = var.cloudwatch_alarms_enabled ? 1 : 0

  alarm_name          = "${local.name}-${var.environment}-alb-5xx-elb"
  alarm_description   = "ALB ${local.name} (${var.environment}) itself is returning 5XX responses. TC-1526."
  namespace           = "AWS/ApplicationELB"
  metric_name         = "HTTPCode_ELB_5XX_Count"
  statistic           = "Sum"
  comparison_operator = "GreaterThanThreshold"
  threshold           = var.alarm_alb_elb_5xx_threshold
  period              = 300
  evaluation_periods  = 1
  datapoints_to_alarm = 1
  treat_missing_data  = "notBreaching"

  dimensions = {
    LoadBalancer = module.alb.arn_suffix
  }

  alarm_actions = [var.alarm_sns_topic_arn]
  ok_actions    = [var.alarm_sns_topic_arn]

  tags = merge(local.tags, { Name = "${local.name}-${var.environment}-alb-5xx-elb" })
}

################################################################################
# Aurora RDS instance alarms
################################################################################

resource "aws_cloudwatch_metric_alarm" "rds_cpu" {
  for_each = var.cloudwatch_alarms_enabled ? local.db_instances : {}

  alarm_name          = "${local.name}-${var.environment}-rds-${each.key}-cpu"
  alarm_description   = "Aurora instance ${local.name}-${each.key} (${var.environment}) CPU utilization is high. TC-1526."
  namespace           = "AWS/RDS"
  metric_name         = "CPUUtilization"
  statistic           = "Average"
  comparison_operator = "GreaterThanThreshold"
  threshold           = var.alarm_rds_cpu_threshold_percent
  period              = 300
  evaluation_periods  = 3
  datapoints_to_alarm = 3
  treat_missing_data  = "missing"

  dimensions = {
    DBInstanceIdentifier = module.db.cluster_instances[each.key].identifier
  }

  alarm_actions = [var.alarm_sns_topic_arn]
  ok_actions    = [var.alarm_sns_topic_arn]

  tags = merge(local.tags, { Name = "${local.name}-${var.environment}-rds-${each.key}-cpu" })
}

resource "aws_cloudwatch_metric_alarm" "rds_freeable_memory" {
  for_each = var.cloudwatch_alarms_enabled ? local.db_instances : {}

  alarm_name          = "${local.name}-${var.environment}-rds-${each.key}-freeable-memory"
  alarm_description   = "Aurora instance ${local.name}-${each.key} (${var.environment}) freeable memory is low (< ${var.alarm_rds_freeable_memory_threshold_percent}% of instance memory). TC-1526."
  namespace           = "AWS/RDS"
  metric_name         = "FreeableMemory"
  statistic           = "Average"
  comparison_operator = "LessThanThreshold"
  threshold           = local.rds_freeable_memory_threshold_bytes
  period              = 300
  evaluation_periods  = 3
  datapoints_to_alarm = 3
  treat_missing_data  = "missing"

  dimensions = {
    DBInstanceIdentifier = module.db.cluster_instances[each.key].identifier
  }

  alarm_actions = [var.alarm_sns_topic_arn]
  ok_actions    = [var.alarm_sns_topic_arn]

  tags = merge(local.tags, { Name = "${local.name}-${var.environment}-rds-${each.key}-freeable-memory" })

  lifecycle {
    precondition {
      condition     = local.rds_instance_memory_bytes != null
      error_message = "No entry for db_instance_class \"${var.db_instance_class}\" in local.rds_instance_class_memory_bytes, and alarm_rds_instance_memory_bytes_override is not set. Add the class's memory in bytes to one of these before enabling cloudwatch_alarms_enabled (TC-1526)."
    }
  }
}

resource "aws_cloudwatch_metric_alarm" "rds_free_local_storage" {
  for_each = var.cloudwatch_alarms_enabled ? local.db_instances : {}

  alarm_name          = "${local.name}-${var.environment}-rds-${each.key}-free-local-storage"
  alarm_description   = "Aurora instance ${local.name}-${each.key} (${var.environment}) free local storage is low. TC-1526."
  namespace           = "AWS/RDS"
  metric_name         = "FreeLocalStorage"
  statistic           = "Minimum"
  comparison_operator = "LessThanThreshold"
  threshold           = var.alarm_rds_free_local_storage_threshold_bytes
  period              = 300
  evaluation_periods  = 1
  datapoints_to_alarm = 1
  treat_missing_data  = "missing"

  dimensions = {
    DBInstanceIdentifier = module.db.cluster_instances[each.key].identifier
  }

  alarm_actions = [var.alarm_sns_topic_arn]
  ok_actions    = [var.alarm_sns_topic_arn]

  tags = merge(local.tags, { Name = "${local.name}-${var.environment}-rds-${each.key}-free-local-storage" })
}

resource "aws_cloudwatch_metric_alarm" "rds_disk_queue_depth" {
  for_each = var.cloudwatch_alarms_enabled ? local.db_instances : {}

  alarm_name          = "${local.name}-${var.environment}-rds-${each.key}-disk-queue-depth"
  alarm_description   = "Aurora instance ${local.name}-${each.key} (${var.environment}) disk queue depth is high. TC-1526."
  namespace           = "AWS/RDS"
  metric_name         = "DiskQueueDepth"
  statistic           = "Average"
  comparison_operator = "GreaterThanThreshold"
  threshold           = var.alarm_rds_disk_queue_depth_threshold
  period              = 300
  evaluation_periods  = 3
  datapoints_to_alarm = 3
  treat_missing_data  = "missing"

  dimensions = {
    DBInstanceIdentifier = module.db.cluster_instances[each.key].identifier
  }

  alarm_actions = [var.alarm_sns_topic_arn]
  ok_actions    = [var.alarm_sns_topic_arn]

  tags = merge(local.tags, { Name = "${local.name}-${var.environment}-rds-${each.key}-disk-queue-depth" })
}
