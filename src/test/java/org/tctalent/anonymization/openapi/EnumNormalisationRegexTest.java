package org.tctalent.anonymization.openapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the enum normalisation regex used in our custom OpenAPI generator templates.
 * <p>
 * This test validates the regex logic that is applied in the generated {@code fromValue()} method
 * of both the {@code enumClass.mustache} (for nested enums) and {@code enumOuterClass.mustache}
 * (for standalone enums) templates. The normalisation converts legacy TC enum values to the Google
 * standard naming convention (see: <a href="https://cloud.google.com/apis/design/naming_convention#enum_names"/>...</a>)
 * used in the TC OpenAPI.
 * <p>
 * Examples:
 * <ul>
 *   <li>"male" is converted to "MALE"</li>
 *   <li>"Family" is converted to "FAMILY"</li>
 *   <li>"CurrentWork" is converted to "CURRENT_WORK"</li>
 *   <li>"autonomousEmployment" is converted to "AUTONOMOUS_EMPLOYMENT"</li>
 *   <li>"OETRead" is converted to "OET_READ</li>
 * </ul>
 *
 * @author sadatmalik
 */
public class EnumNormalisationRegexTest {

  // Helper method that applies the normalization
  // This applies the regex that is used to convert all ingested enum values from the TC server to
  // Google standard naming conventions for API enums
  // See: https://cloud.google.com/apis/design/naming_convention#enum_names
  private String normalise(String value) {
    // Insert an underscore between a sequence of uppercase letters and an uppercase letter followed by a lowercase letter.
    String step1 = value.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2");
    // Insert an underscore between a lowercase letter and an uppercase letter.
    return step1.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
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

  @Test
  public void testNormaliseOetRead() {
    assertEquals("OET_READ", normalise("OETRead"),
        "Expected 'OETRead' to normalize to 'OET_READ'");
  }

}
