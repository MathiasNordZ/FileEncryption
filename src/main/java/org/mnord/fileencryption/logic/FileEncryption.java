package org.mnord.fileencryption.logic;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

public class FileEncryption {
  private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
  private static final int IV = 16;
  private static final int SALT_SIZE = 16;
  private static final int KEY_SIZE = 256;
  private static final int ITERATION_COUNT = 65536;

  public void encrypt(String inputFIle, String outputFile, String password) throws Exception {
    byte[] salt = new byte[SALT_SIZE];

    SecureRandom random = new SecureRandom();
    random.nextBytes(salt);

    SecretKey secretKey = generateKey(password, salt);

    byte[] iv = new byte[IV];
    random.nextBytes(iv);
    IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
  }

}
