package org.tctalent.anonymization.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * An {@link AccessDeniedHandler} implementation that handles access denied failures by
 * returning a Problem Details JSON response compliant with RFC 9457.
 * <p/>
 * When an authenticated request attempts to access a protected resource with insufficient
 * authority, this handler is invoked, generating a response with an HTTP 403 (Forbidden) status.
 * The response content type is set to {@code application/problem+json} to indicate that it conforms
 * to the Problem Details specification.
 * <p/>
 * Example response:
 * <pre>
 * {
 *   "type": "about:blank",
 *   "title": "Forbidden",
 *   "status": 403,
 *   "detail": "Access Denied"
 *   "instance": "/v1/candidates"
 * }
 * </pre>
 *
 * @author sadatmalik
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    // Build a ProblemDetail object with 403 status and a custom message
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, accessDeniedException.getMessage());
    problemDetail.setInstance(URI.create(request.getRequestURI()));
    response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    mapper.writeValue(response.getOutputStream(), problemDetail);
  }
}
