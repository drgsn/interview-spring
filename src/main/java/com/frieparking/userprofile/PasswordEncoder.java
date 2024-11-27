package com.frieparking.userprofile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoder {

  private final SecureRandom random;
  private static final int SALT_LENGTH = 16;

  public PasswordEncoder() {
    this.random = new SecureRandom();
  }

  public String encode(String rawPassword) {
    byte[] salt = generateSalt();
    byte[] hashedPassword = hashPassword(rawPassword, salt);

    byte[] combined = new byte[salt.length + hashedPassword.length];
    System.arraycopy(salt, 0, combined, 0, salt.length);
    System.arraycopy(hashedPassword, 0, combined, salt.length, hashedPassword.length);

    return Base64.getEncoder().encodeToString(combined);
  }

  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    try {
      byte[] combined = Base64.getDecoder().decode(encodedPassword);

      byte[] salt = new byte[SALT_LENGTH];
      byte[] hash = new byte[combined.length - SALT_LENGTH];
      System.arraycopy(combined, 0, salt, 0, SALT_LENGTH);
      System.arraycopy(combined, SALT_LENGTH, hash, 0, hash.length);

      byte[] testHash = hashPassword(rawPassword.toString(), salt);

      return MessageDigest.isEqual(hash, testHash);
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  private byte[] generateSalt() {
    byte[] salt = new byte[SALT_LENGTH];
    random.nextBytes(salt);
    return salt;
  }

  private byte[] hashPassword(String password, byte[] salt) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      digest.update(salt);
      return digest.digest(password.getBytes());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Error hashing password", e);
    }
  }
}
