package org.tctalent.anonymization.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class AuthenticationFilter extends OncePerRequestFilter {

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws IOException, ServletException {
    try {
      Authentication authentication = AuthenticationService.getAuthentication(request);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception exp) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      PrintWriter writer = response.getWriter();
      writer.print(exp.getMessage());
      writer.flush();
      writer.close();
    }

    filterChain.doFilter(request, response);
  }

}
