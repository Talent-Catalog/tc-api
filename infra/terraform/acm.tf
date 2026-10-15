################################################################################
# ACM Certificate
################################################################################

resource "aws_acm_certificate" "this" {
  count = var.manage_public_dns ? 1 : 0

  domain_name       = var.site_domain
  validation_method = "DNS"

  lifecycle {
    create_before_destroy = true
  }

  tags = local.tags
}

# DNS validation records for ACM certificate
resource "aws_route53_record" "certificate_validation" {
  for_each = var.manage_public_dns ? {
    for dvo in aws_acm_certificate.this[0].domain_validation_options : dvo.domain_name => {
      name   = dvo.resource_record_name
      type   = dvo.resource_record_type
      record = dvo.resource_record_value
    }
  } : {}

  zone_id = aws_route53_zone.this[0].zone_id
  name    = each.value.name
  type    = each.value.type
  ttl     = 60
  records = [each.value.record]
}

# Waits for DNS validation to complete before the certificate is usable
resource "aws_acm_certificate_validation" "this" {
  count = var.manage_public_dns ? 1 : 0

  certificate_arn         = aws_acm_certificate.this[0].arn
  validation_record_fqdns = [for r in aws_route53_record.certificate_validation : r.fqdn]
}
