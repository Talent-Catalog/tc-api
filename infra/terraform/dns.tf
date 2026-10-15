################################################################################
# Route53 DNS
################################################################################

resource "aws_route53_zone" "this" {
  count = var.manage_public_dns ? 1 : 0

  name = var.site_domain

  tags = local.tags
}

# A record pointing the domain to the ALB
resource "aws_route53_record" "ipv4" {
  count = var.manage_public_dns ? 1 : 0

  zone_id = aws_route53_zone.this[0].zone_id
  name    = var.site_domain
  type    = "A"

  alias {
    name                   = module.alb.dns_name
    zone_id                = module.alb.zone_id
    evaluate_target_health = false
  }
}

resource "aws_route53_record" "www" {
  count = var.manage_public_dns ? 1 : 0

  zone_id = aws_route53_zone.this[0].zone_id
  name    = "www"
  type    = "CNAME"
  ttl     = "600"
  records = [var.site_domain]
}
