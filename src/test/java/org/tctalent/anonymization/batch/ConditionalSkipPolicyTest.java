package org.tctalent.anonymization.batch;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tctalent.anonymization.batch.config.ConditionalSkipPolicy;
import org.tctalent.anonymization.exception.RestApiReaderException;

/**
 * Unit tests for the {@link ConditionalSkipPolicy} class.
 *
 * @author sadatmalik
 */
class ConditionalSkipPolicyTest {

  private ConditionalSkipPolicy skipPolicy;

  @BeforeEach
  void setUp() {
    skipPolicy = new ConditionalSkipPolicy(3); // Set max skips to 3 for testing
  }

  @Test
  @DisplayName("Test should skip on non-RestApiReaderException")
  void testShouldSkip_NonRestApiReaderException() {
    Throwable nonRestApiException = new RuntimeException("Non-RestApiReaderException");
    assertTrue(skipPolicy.shouldSkip(nonRestApiException, 0));
  }

  @Test
  @DisplayName("Test should skip on RestApiReaderException within limit")
  void testShouldSkip_RestApiReaderException_WithinLimit() {
    Throwable restApiException = new RestApiReaderException("RestApiReaderException");

    assertTrue(skipPolicy.shouldSkip(restApiException, 0));
    assertTrue(skipPolicy.shouldSkip(restApiException, 1));
    assertTrue(skipPolicy.shouldSkip(restApiException, 2));
  }

  @Test
  @DisplayName("Test should fail job on RestApiReaderException exceeds limit")
  void testShouldSkip_RestApiReaderException_ExceedsLimit() {
    Throwable restApiException = new RestApiReaderException("RestApiReaderException");

    assertTrue(skipPolicy.shouldSkip(restApiException, 0));
    assertTrue(skipPolicy.shouldSkip(restApiException, 1));
    assertTrue(skipPolicy.shouldSkip(restApiException, 2));
    assertFalse(skipPolicy.shouldSkip(restApiException, 3)); // Exceeds limit
  }
}
