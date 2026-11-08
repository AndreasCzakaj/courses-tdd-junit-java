package eu.binarystars.tdd.uss;

import java.time.Instant;

/**
 * @param passwordHash format: "&lt;salt&gt;:&lt;hash&gt;", see {@link Passwords}
 */
public record Account(String id, String username, String passwordHash, String email,
                      Instant tcAccepted, String status) {
    public static final String STATUS_VERIFIED = "verified";

    public boolean isVerified() {
        return STATUS_VERIFIED.equals(status);
    }
}
