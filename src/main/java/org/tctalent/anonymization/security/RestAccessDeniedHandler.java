package org.tctalent.anonymization.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.tctalent.anonymization.exception.GlobalExceptionHandler;

/**
 * An {@link AccessDeniedHandler} implementation that handles access denied failures by
 * delegating exception processing to the global exception handler. When an authenticated request
 * attempts to access a protected resource without the required authority, this handler is invoked.
 * It delegates the exception to a {@link HandlerExceptionResolver}, which routes the exception to
 * the global exception handler, which constructs a Problem Details JSON response compliant with
 * RFC 9457.
 * <p>
 * The generated response has an HTTP 403 (Forbidden) status, and the content type is set to
 * {@code application/problem+json}.
 * <p>
 * Example response:
 * <pre>
 * {
 *   "type": "about:blank",
 *   "title": "Forbidden",
 *   "status": 403,
 *   "detail": "Access Denied",
 *   "instance": "/v1/candidates"
 * }
 * </pre>
 *
 * @see GlobalExceptionHandler
 * @author sadatmalik
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

  @Autowired
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver resolver;


  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    // Delegate the exception handling to our GlobalExceptionHandler
    resolver.resolveException(request, response, null, accessDeniedException);
  }
}
