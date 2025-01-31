package org.tctalent.anonymization.batch;

import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.batch.config.BatchProperties;
import org.tctalent.anonymization.exception.RestApiReaderException;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.model.IdentifiableCandidatePage;
import org.tctalent.anonymization.service.TalentCatalogService;

/**
 * An {@link ItemReader} implementation that reads data from the {@link TalentCatalogService},
 * fetching paginated batches of {@link IdentifiableCandidate} objects.
 * </p>
 * Rate limiting is applied to avoid hitting the API too frequently.
 *
 * @author sadatmalik
 */
@Slf4j
@Component
@Qualifier("restApiItemReader")
public class RestApiItemReader implements ItemReader<IdentifiableCandidate> {

  private final BatchProperties batchProperties;
  private final TalentCatalogService talentCatalogService;

  private int currentPage = 0;
  private int totalPages = 1; // Initialise with 1 to start fetching
  private Iterator<IdentifiableCandidate> batchIterator;

  private final long fetchDelayMillis;
  private long lastFetchTime = 0;

  public RestApiItemReader(TalentCatalogService talentCatalogService, BatchProperties batchProperties) {
    this.batchProperties = batchProperties;
    this.talentCatalogService = talentCatalogService;
    this.fetchDelayMillis = batchProperties.getFetchDelayMillis();
  }

  @Override
  public IdentifiableCandidate read() throws RestApiReaderException {
    try {
      if (batchIterator == null || !batchIterator.hasNext()) {
        fetchNextBatch();
      }
      return batchIterator != null && batchIterator.hasNext() ? batchIterator.next() : null;
    } catch (RestApiReaderException e) {
      throw e;
    } catch (Exception e) {
      throw new RestApiReaderException("Failed to read IdentifiableCandidate from REST API", e);
    }
  }

  private void fetchNextBatch() {
    if (!talentCatalogService.isLoggedIn()) {
      talentCatalogService.login();
    }
    if (currentPage < totalPages) {
      applyRateLimiting();

      // Fetch next batch
      IdentifiableCandidatePage page = talentCatalogService
          .fetchPageOfIdentifiableCandidateData(currentPage, batchProperties.getPageSize());

      if (page == null || page.getContent() == null) {
        throw new RestApiReaderException("Received null or invalid response from the REST API");
      }

      List<IdentifiableCandidate> currentBatch = page.getContent();
      totalPages = page.getTotalPages();
      batchIterator = currentBatch.iterator();
      currentPage++;

      lastFetchTime = System.currentTimeMillis(); // Update fetch timestamp
    }
  }

  private void applyRateLimiting() {
    long now = System.currentTimeMillis();
    long timeSinceLastFetch = now - lastFetchTime;

    if (timeSinceLastFetch < fetchDelayMillis) {
      long remainingDelay = fetchDelayMillis - timeSinceLastFetch;

      try {
        log.debug("Sleeping for {} ms to avoid hitting the API too frequently", remainingDelay);
        Thread.sleep(remainingDelay); // Sleep remaining time
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RestApiReaderException("Fetch was interrupted during rate limiting", e);
      }
    }
  }
}
