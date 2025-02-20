package org.tctalent.anonymization.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An {@link AuthenticationEntryPoint} implementation that handles authentication failures by
 * returning a Problem Details JSON response compliant with RFC 9457.
 * <p/>
 * When an unauthenticated request attempts to access a protected resource, this entry point is
 * invoked, generating a response with an HTTP 401 (Unauthorized) status and a detailed error
 * message in the body. The response content type is set to {@code application/problem+json} to
 * indicate that it conforms to the Problem Details specification.
 * <p/>
 * Example response:
 * <pre>
 * {
 *   "type": "about:blank",
 *   "title": "Unauthorized",
 *   "status": 401,
 *   "detail": "Invalid API Key"
 * }
 * </pre>
 * </p>
 *
 * @author sadatmalik
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    // Build a ProblemDetails object with 401 status and a custom message
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, authException.getMessage());
    response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    mapper.writeValue(response.getOutputStream(), problemDetail);
  }
}
