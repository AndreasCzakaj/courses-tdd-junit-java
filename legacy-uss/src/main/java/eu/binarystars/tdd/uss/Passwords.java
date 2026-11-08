package eu.binarystars.tdd.uss;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HexFormat;

public final class Passwords {
    static final int SALT_BYTES = 16;
    static final int HASH_BITS = 512;
    static final int ITERATIONS = 100_000;
    static final String SEPARATOR = ":";

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final HexFormat HEX = HexFormat.of();

    private Passwords() {
    }

    /**
     * @return "&lt;salt&gt;:&lt;hash&gt;", both hex encoded
     */
    public static String hash(final String password) {
        final byte[] salt = new byte[SALT_BYTES];
        RANDOM.nextBytes(salt);
        return hash(password, HEX.formatHex(salt));
    }

    public static String hash(final String password, final String salt) {
        return salt + SEPARATOR + calcHash(password, salt);
    }

    public static boolean verify(final String password, final String passwordHash) {
        final String[] parts = passwordHash.split(SEPARATOR, 2);
        final String actual = calcHash(password, parts[0]);
        // constant time: the duration must not reveal how many chars match
        return MessageDigest.isEqual(
                actual.getBytes(StandardCharsets.UTF_8),
                parts[parts.length - 1].getBytes(StandardCharsets.UTF_8));
    }

    static String calcHash(final String password, final String salt) {
        try {
            final var spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), ITERATIONS, HASH_BITS);
            return HEX.formatHex(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded());
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("PBKDF2 not available", e);
        }
    }
}
