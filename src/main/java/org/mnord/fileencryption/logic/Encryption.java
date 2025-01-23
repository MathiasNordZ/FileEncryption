package org.mnord.fileencryption.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/**
 * The Encryption class provides methods to encrypt and decrypt files using AES/GCM/NoPadding.
 * It uses a SecretKey for encryption and decryption operations.
 */
public class Encryption {
  private Cipher cipher;

  /**
   * Encrypts the given input file using the provided secret key.
   * The encrypted data is written back to the original file.
   *
   * @param inputFile the file to be encrypted
   * @param secretKey the secret key used for encryption
   * @throws RuntimeException if an error occurs during encryption
   */
  public void encrypt(File inputFile, SecretKey secretKey) {
    try {
      cipher = Cipher.getInstance("AES/GCM/NoPadding");
      byte[] iv = new byte[16];
      SecureRandom random = new SecureRandom();
      random.nextBytes(iv);
      GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

      File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");

      try (FileInputStream inputStream = new FileInputStream(inputFile);
           FileOutputStream outputStream = new FileOutputStream(tempFile)) {

        outputStream.write(iv);
        cryptoLogic(inputStream, outputStream);
      }

      if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
        throw new IOException("Failed to overwrite the original file.");
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  /**
   * Decrypts the given input file using the provided secret key.
   * The decrypted data is written back to the original file.
   *
   * @param inputFile the file to be decrypted
   * @param secretKey the secret key used for decryption
   * @throws RuntimeException if an error occurs during decryption
   */
  public void decrypt(File inputFile, SecretKey secretKey) {
    try {
      cipher = Cipher.getInstance("AES/GCM/NoPadding");
      File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");

      try (FileInputStream inputStream = new FileInputStream(inputFile);
           FileOutputStream outputStream = new FileOutputStream(tempFile)) {

        byte[] iv = new byte[16];
        inputStream.read(iv);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

        cryptoLogic(inputStream, outputStream);
      }

      if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
        throw new IOException("Failed to overwrite the original file.");
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  /**
   * Performs the core encryption or decryption logic.
   *
   * @param inputStream  the input stream of the file
   * @param outputStream the output stream to write the processed data
   * @throws IOException if an I/O error occurs
   * @throws IllegalBlockSizeException if the block size is incorrect
   * @throws BadPaddingException if the padding is incorrect
   */
  private void cryptoLogic(FileInputStream inputStream, FileOutputStream outputStream)
      throws IOException, IllegalBlockSizeException, BadPaddingException {
    byte[] buffer = new byte[4096];
    int bytesRead;

    while ((bytesRead = inputStream.read(buffer)) != -1) {
      byte[] output = cipher.update(buffer, 0, bytesRead);
      if (output != null) {
        outputStream.write(output);
      }
    }
    byte[] outputBytes = cipher.doFinal();
    if (outputBytes != null) {
      outputStream.write(outputBytes);
    }
  }
}