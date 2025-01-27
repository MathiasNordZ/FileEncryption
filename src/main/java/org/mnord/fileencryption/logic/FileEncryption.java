package org.mnord.fileencryption.logic;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class FileEncryption {
  private static final String ALGORITHM = "AES";
  private static final String TRANSFORMATION = "AES";

  public void encrypt(String key, File inputFile, File outputFile) throws CryptoException {
    cryptoLogic(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
  }

  public void decrypt(String key, File inputFile, File outputFile) throws CryptoException {
    cryptoLogic(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
  }

  private static void cryptoLogic (int cipherMode, String key, File inputFile, File outputFile) throws CryptoException {
    try {
      Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(cipherMode, secretKey);

      FileInputStream inputStream = new FileInputStream(inputFile);
      byte[] inputBytes = new byte[(int) inputFile.length()];
      inputStream.read();

      byte[] outputBytes = cipher.doFinal(inputBytes);
      FileOutputStream outputStream = new FileOutputStream(outputFile);

      outputStream.write(outputBytes);

      inputStream.close();
      outputStream.close();
    } catch (NoSuchAlgorithmException
             | NoSuchPaddingException
             | InvalidKeyException
             | IOException
             | IllegalBlockSizeException
             | BadPaddingException e) {
      throw new CryptoException("Error in encryption/decryption");
    }
  }
}