package org.tctalent.anonymization.batch;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.tctalent.anonymization.batch.config.BatchProperties;

/**
 * Test for {@link BatchProperties}.
 *
 * @author sadatmalik
 */
@SpringBootTest(classes = BatchProperties.class)
@EnableConfigurationProperties(BatchProperties.class)
@TestPropertySource(properties = {
    "batch.chunk-size=100",
    "batch.page-size=200"
})
class BatchPropertiesTest {

  @Autowired
  BatchProperties batchProperties;

  @Test
  @DisplayName("Test batch properties")
  void testBatchProperties() {
    assertEquals(100, batchProperties.getChunkSize());
    assertEquals(200, batchProperties.getPageSize());
  }
}

