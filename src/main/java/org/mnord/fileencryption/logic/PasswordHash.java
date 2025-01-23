package org.mnord.fileencryption.logic;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

  /**
   * This class provides functionality for hashing passwords using the PBKDF2
   * algorithm with HMAC-SHA512. It includes methods to convert a string password
   * into a hashed representation.
   * The hashing process uses a fixed salt and a specified number of iterations
   * and key length to generate a secure hash.
   */
  public class PasswordHash {
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final byte[] SALT = new byte[128];
    private SecretKey key;

    /**
     * Initializes a new instance of the PasswordHashing class.
     * Generates a secure random salt for use in the password hashing process.
     */
    public PasswordHash() {
      SecureRandom secureRandom = new SecureRandom();
      secureRandom.nextBytes(SALT);
    }

    public SecretKey getKey() {
      return this.key;
    }

    /**
     * Converts a provided password into a hashed representation.
     *
     * @param providedPassword the password to be hashed
     */
    public void stringToHash(String providedPassword) {
      this.key = hashPassword(providedPassword.toCharArray());
    }

    /**
     * Hashes the provided password using PBKDF2 with HMAC-SHA512.
     *
     * @param password the password to be hashed as a character array
     * @return the hashed password as a byte array
     * @throws RuntimeException if the hashing process encounters an error
     */
    private SecretKey hashPassword(final char[] password) {
      try {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(password, SALT, ITERATIONS, KEY_LENGTH);
        byte[] keyBytes = secretKeyFactory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
      } catch (Exception e) {
        throw new RuntimeException();
      }
    }
  }
