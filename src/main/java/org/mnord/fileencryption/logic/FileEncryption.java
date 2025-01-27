package org.mnord.fileencryption.logic;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class FileEncryption {
  private static final String ALGORITHM = "AES";
  private static final String TRANSFORMATION = "AES";

  private void cryptoLogic (int cipherMode, String key, File inputFile, File outputFile) throws CryptoException {
    try {
      Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(cipherMode, secretKey);


    } catch (NoSuchAlgorithmException
             | NoSuchPaddingException
             | InvalidKeyException e) {

    }
  }
}