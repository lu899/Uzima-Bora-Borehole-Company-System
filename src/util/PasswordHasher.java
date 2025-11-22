package util;

import java.security.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.*;

public class PasswordHasher {
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    public static String hashPassword(char[] password) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        byte[] hash = pbkdf2(password, salt, ITERATIONS, KEY_LENGTH);

        byte[] combined = new byte[salt.length + hash.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(hash, 0, combined, salt.length, hash.length);

        return Base64.getEncoder().encodeToString(combined);
    }
    
    public static boolean verifyPassword(char[] password, String storedHash) throws Exception{
        byte[] combined = Base64.getDecoder().decode(storedHash);

        byte[] salt = new byte[16];
        System.arraycopy(combined, 0, salt, 0, 16);

        byte[] hash = new byte[combined.length - 16];
        System.arraycopy(combined, 16, hash, 0, hash.length);

        byte[] testHash = pbkdf2(password, salt, ITERATIONS, KEY_LENGTH);
        return MessageDigest.isEqual(hash, testHash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyLength) throws Exception{
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        spec.clearPassword();
        return hash;
    }
        
}
