package org.mnord.fileencryption.logic;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class FileEncryption {
  private byte[] inputFileBytes;
  private static final byte[] IV = new byte[16];

  private void fileToBytes(File inputFile) throws CryptoException {
    if (!inputFile.exists()) {
      throw new CryptoException("Input file does not exist!");
    }
    try {
      inputFileBytes = Files.readAllBytes(inputFile.toPath());
    } catch (IOException e) {
      throw new CryptoException("Error during conversion to bytes!", e);
    }
  }

  public void encrypt(File inputFile, SecretKey key) throws CryptoException {
    fileToBytes(inputFile); // Ensure inputFileBytes is initialized

    SecureRandom random = new SecureRandom();
    random.nextBytes(IV);
    IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);

    Cipher cipher;
    try {
      cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
    } catch (NoSuchAlgorithmException
             | NoSuchPaddingException
             | InvalidKeyException
             | InvalidAlgorithmParameterException e) {
      throw new CryptoException("Error during encryption!", e);
    }

    try (FileOutputStream fileOut = new FileOutputStream(inputFile);
         CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {
      fileOut.write(IV);
      cipherOut.write(inputFileBytes);
    } catch (IOException e) {
      throw new CryptoException("Error during encryption!", e);
    }
  }

  public void decrypt(File inputFile, SecretKey key, File outputFile) throws CryptoException {
    try (FileInputStream fileIn = new FileInputStream(inputFile)) {
      // Read the IV from the file
      if (fileIn.read(IV) != IV.length) {
        throw new CryptoException("Error reading IV from file!");
      }
      IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);

      try (CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
           FileOutputStream fileOut = new FileOutputStream(outputFile)) {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = cipherIn.read(buffer)) != -1) {
          fileOut.write(buffer, 0, bytesRead);
        }
      }
    } catch (NoSuchAlgorithmException
             | NoSuchPaddingException
             | InvalidKeyException
             | InvalidAlgorithmParameterException
             | IOException e) {
      throw new CryptoException("Error during decryption!", e);
    }
  }
}