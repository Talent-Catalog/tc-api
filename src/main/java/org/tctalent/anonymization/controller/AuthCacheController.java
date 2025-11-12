package org.tctalent.anonymization.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tctalent.anonymization.security.AuthenticationService;

/**
 * REST controller that provides endpoints to manage the authentication cache.
 * <p>
 * Allows evicting cached authentication for a specific API key or clearing the entire cache.
 *
 * @author sadatmalik
 */
@RestController
@RequestMapping("/internal/auth-cache")
class AuthCacheController {
  private final AuthenticationService svc;

  AuthCacheController(AuthenticationService svc) {
    this.svc = svc;
  }

  /**
   * Evicts the cached authentication for the given API key.
   *
   * @param apiKey the API key whose cached authentication should be evicted
   */
  @DeleteMapping("/keys/{apiKey}")
  public void evict(@PathVariable String apiKey) {
    svc.evictKey(apiKey.trim());
  }

  /**
   * Clears the entire authentication cache.
   */
  @DeleteMapping
  public void clearAll() {
    svc.clearCache();
  }
}
