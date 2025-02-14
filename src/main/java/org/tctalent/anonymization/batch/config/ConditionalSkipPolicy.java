package org.tctalent.anonymization.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.skip.SkipPolicy;
import java.util.concurrent.atomic.AtomicInteger;
import org.tctalent.anonymization.exception.RestApiReaderException;
import org.tctalent.anonymization.logging.LogBuilder;

/**
 * Skip policy that implements the {@link SkipPolicy} interface to control the number of skips
 * allowed for {@link RestApiReaderException} exceptions.
 * </p>
 * If there are a significantly large number of read errors from the REST API, the job configured
 * with this policy will fail. This is to prevent the job from running indefinitely if the REST API
 * is not responding as expected.
 * </p>
 * Used as part of the candidate migration batch process.
 *
 * @author sadatmalik
 */
@Slf4j
public class ConditionalSkipPolicy implements SkipPolicy {

  private final AtomicInteger restApiReaderExceptionCount = new AtomicInteger(0);
  private final int maxRestApiReaderSkips;

  public ConditionalSkipPolicy(int maxRestApiReaderSkips) {
    this.maxRestApiReaderSkips = maxRestApiReaderSkips;
  }

  @Override
  public boolean shouldSkip(Throwable t, long skipCount) {
    // Always skip for non-RestApiReaderException
    if (!(t instanceof RestApiReaderException)) {
      return true;
    }

    // For RestApiReaderException, count skips and fail if limit exceeded
    int currentCount = restApiReaderExceptionCount.incrementAndGet();

    if (currentCount > maxRestApiReaderSkips) {
      LogBuilder.builder(log)
          .action("Skip policy")
          .message("Max skip limit for RestApiReaderException reached: " +  currentCount + " skips")
          .logError();

      return false; // Do not skip, fail the job
    }

    return true; // Skip within the limit
  }
}
