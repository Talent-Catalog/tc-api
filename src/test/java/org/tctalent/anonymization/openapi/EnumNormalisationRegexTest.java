package org.tctalent.anonymization.openapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class EnumNormalisationRegexTest {

  // Helper method that applies the normalization
  // This applies the regex that is used to convert all ingested enum values from the TC server to
  // Google standard naming conventions for API enums
  // See: https://cloud.google.com/apis/design/naming_convention#enum_names
  private String normalise(String value) {
    return value.replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase();
  }

  @Test
  public void testNormaliseMale() {
    assertEquals("MALE", normalise("male"),
        "Expected 'male' to normalize to 'MALE'");
  }

  @Test
  public void testNormaliseFamily() {
    assertEquals("FAMILY", normalise("Family"),
        "Expected 'Family' to normalize to 'FAMILY'");
  }

  @Test
  public void testNormaliseCurrentWork() {
    assertEquals("CURRENT_WORK", normalise("CurrentWork"),
        "Expected 'CurrentWork' to normalize to 'CURRENT_WORK'");
  }

  @Test
  public void testNormaliseAutonomousEmployment() {
    assertEquals("AUTONOMOUS_EMPLOYMENT", normalise("autonomousEmployment"),
        "Expected 'autonomousEmployment' to normalize to 'AUTONOMOUS_EMPLOYMENT'");
  }
}
