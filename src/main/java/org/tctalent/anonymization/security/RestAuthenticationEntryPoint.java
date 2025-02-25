package org.tctalent.anonymization.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.tctalent.anonymization.exception.GlobalExceptionHandler;

/**
 * An {@link AuthenticationEntryPoint} implementation that handles authentication failures by
 * delegating error response construction to the global exception handler. When an unauthenticated
 * request attempts to access a protected resource, this entry point is invoked. It delegates the
 * exception to a {@link HandlerExceptionResolver}, which routes the exception to the global exception
 * handler, which constructs a Problem Details JSON response compliant with RFC 9457.
 * <p>
 * The generated response has an HTTP 401 (Unauthorized) status and a detailed error message in the
 * body. The response content type is set to {@code application/problem+json} to indicate that it
 * conforms to the Problem Details specification.
 * <p>
 * Example response:
 * <pre>
 * {
 *   "type": "about:blank",
 *   "title": "Unauthorized",
 *   "status": 401,
 *   "detail": "Invalid API Key"
 * }
 * </pre>
 *
 * @see <a href="https://www.baeldung.com/spring-security-exceptionhandler">
 * @see GlobalExceptionHandler
 * @author sadatmalik
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Autowired
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver resolver;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    // Delegate exception handling to the global exception handler via the resolver
    resolver.resolveException(request, response, null, authException);
  }
}
