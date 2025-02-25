package org.tctalent.anonymization.exception;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import java.net.URI;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.stream.Collectors;

/**
 * Global exception handler that converts exceptions into Problem Details responses.
 * <p>
 * It can override exception handles if needed. For example to process validation errors (e.g.
 * MethodArgumentNotValidException) and deserialization errors (e.g. HttpMessageNotReadableException)
 * <p>
 * It defines exception handlers for authentication and access denied errors. These handlers are
 * invoked via delegation from the Spring Security configuration's {@code AuthenticationEntryPoint}
 * and {@code AccessDeniedHandler}
 *
 * @see ResponseEntityExceptionHandler
 * @see ProblemDetail
 * @see MethodArgumentNotValidException
 * @see HttpMessageNotReadableException
 * @author sadatmalik
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handles validation errors when a controller method argument annotated with @Valid fails.
   * <p>
   * Aggregates all field error messages into a single error string and wraps it in a
   * ProblemDetail object with a BAD_REQUEST (400) status. The response is then built using
   * a helper method defined in Spring's ResponseEntityExceptionHandler.
   *
   * @param ex the exception containing validation errors
   * @param headers the HTTP headers of the request
   * @param status the HTTP status code
   * @param request the current web request
   * @return a ResponseEntity containing a ProblemDetail body with status 400
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    String errors = ex.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
        .collect(Collectors.joining("; "));

    if (errors.isBlank()) {
      errors = "Validation failed for the request.";
    }

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errors);
    return createResponseEntity(problemDetail, headers, status, request);
  }

  /**
   * Handles HttpMessageNotReadableException by checking if the underlying cause is a
   * ValueInstantiationException caused by an IllegalArgumentException. If so, it constructs a
   * ProblemDetail response with a 400 Bad Request status using the error message. Otherwise, it
   * delegates to the default exception handling.
   * <p>
   * This is useful when a ValueInstantiationException is thrown due to an invalid enum value in a
   * request body. The exception is caught, and the error message is extracted from the
   * IllegalArgumentException and returned in the response.
   *
   * @param ex the HttpMessageNotReadableException encountered during request deserialization
   * @param headers the HTTP headers associated with the request
   * @param status the HTTP status code
   * @param request the current web request
   * @return a ResponseEntity containing a ProblemDetail with a 400 status if a specific cause is
   * found, or the default handling response otherwise
   */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    // Check if the cause is a ValueInstantiationException
    Throwable cause = ex.getCause();
    if (cause instanceof ValueInstantiationException vie) {
      // Retrieve the inner cause
      Throwable innerCause = vie.getCause();
      if (innerCause instanceof IllegalArgumentException iae) {
        String message = iae.getMessage();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
        return createResponseEntity(problemDetail, headers, status, request);
      }
    }

    // Fallback to default handling if it's not the exception we're expecting
    return super.handleHttpMessageNotReadable(ex, headers, status, request);
  }

  /**
   * Handles AuthenticationException instances thrown during security processing.
   * <p>
   * This method constructs a ProblemDetail object with a 401 (Unauthorized) status, populates it
   * with the exception message and the request URI, and returns it in the response. The exception
   * is typically delegated to this handler via the AuthenticationEntryPoint.
   *
   * @param ex the AuthenticationException thrown during authentication failure
   * @param request the current web request
   * @return a ResponseEntity containing a ProblemDetail with a 401 status
   */
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
    // Set the instance URI based on the request
    problemDetail.setInstance(getInstanceUri(request));

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
    return createResponseEntity(problemDetail, headers, HttpStatus.UNAUTHORIZED, request);
  }

  /**
   * Handles AccessDeniedException instances thrown when an authenticated user lacks sufficient
   * privileges.
   * <p>
   * This method constructs a ProblemDetail object with a 403 (Forbidden) status, populates it with
   * the exception message and the request URI, and returns it in the response. The exception is
   * typically delegated to this handler via the AccessDeniedHandler.
   *
   * @param ex the AccessDeniedException thrown due to insufficient access rights
   * @param request the current web request
   * @return a ResponseEntity containing a ProblemDetail with a 403 status
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
    // Set the instance URI based on the request
    problemDetail.setInstance(getInstanceUri(request));

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
    return createResponseEntity(problemDetail, headers, HttpStatus.FORBIDDEN, request);
  }

  private URI getInstanceUri(WebRequest request) {
    String instanceUri = request.getDescription(false).replace("uri=", "");
    return URI.create(instanceUri.isBlank() ? "/" : instanceUri);
  }

}
