package org.tctalent.anonymization.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * A custom security filter that evaluates the API Key header and set the resulting Authentication
 * object into the current SecurityContext instance. Then, the request is passed to the remaining
 * filters for processing and then routed to DispatcherServlet and finally to our controller.
 * <p/>
 * We delegate the evaluation of the API Key and constructing the Authentication object to the
 * AuthenticationService class.
 *
 * @see <a href="https://www.baeldung.com/spring-boot-api-key-secret">
 * @author sadatmalik
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

  private final AuthenticationEntryPoint authenticationEntryPoint;

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws IOException, ServletException {
    try {
      Authentication authentication = AuthenticationService.getAuthentication(request);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception exp) {
      // Clear any authentication context
      SecurityContextHolder.clearContext();
      // Delegate to the authentication entry point which will build a ProblemDetails response
      authenticationEntryPoint.commence(request, response,
          new BadCredentialsException("Invalid API Key", exp));
      return;
    }

    filterChain.doFilter(request, response);
  }

}
