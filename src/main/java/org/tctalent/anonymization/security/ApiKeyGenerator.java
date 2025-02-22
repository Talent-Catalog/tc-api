package org.tctalent.anonymization.security;

import java.security.SecureRandom;
import java.util.Base64;


/**
 * A utility class that generates a random API key for use in the authentication process.
 * <p/>
 * The generateApiKey method creates a 256-bit random byte array and encodes it using Base64
 * encoding. The resulting string is a 44-character long API key that can be used to authenticate
 * requests.
 * <p/>
 * The SecureRandom class provides a cryptographically strong random number generator (RNG) that
 * generates random bytes suitable for creating secret keys. The Base64 class provides encoding
 * schemes for converting binary data to printable characters and vice versa.
 *
 * @see <a href="https://stackoverflow.com/questions/14412132/whats-the-best-approach-for-generating-a-new-api-key">
 * @author sadatmalik
 */
public class ApiKeyGenerator {
  private static final SecureRandom secureRandom = new SecureRandom();
  private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

  public static String generateApiKey() {
    byte[] randomBytes = new byte[32]; // 256 bits for strong entropy
    secureRandom.nextBytes(randomBytes);
    return base64Encoder.encodeToString(randomBytes);
  }
}
