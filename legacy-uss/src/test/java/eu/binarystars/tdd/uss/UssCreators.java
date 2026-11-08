package eu.binarystars.tdd.uss;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Domain specific "creators": create a valid object ... then break it in 1 place.
 */
public final class UssCreators {
    public static final String USERNAME = "alice_verified";
    public static final String PASSWORD = "Correct-Horse_42";
    // hashing is slow on purpose => hash once for all tests
    public static final String PASSWORD_HASH = Passwords.hash(PASSWORD);

    private UssCreators() {
    }

    public static Map<String, Object> createValidCredentials() {
        return new HashMap<>(Map.of("username", USERNAME, "password", PASSWORD));
    }

    public static Map<String, Object> createCredentials(final String key, final Object value) {
        final var credentials = createValidCredentials();
        credentials.put(key, value);
        return credentials;
    }

    public static Account createVerifiedAccount() {
        return new Account("id-1", USERNAME, PASSWORD_HASH, "alice@example.com",
                Instant.parse("2026-01-01T00:00:00Z"), Account.STATUS_VERIFIED);
    }

    public static Account createNewAccount() {
        return createAccount("id-1", USERNAME, "new");
    }

    public static Account createAccount(final String id, final String username, final String status) {
        final var account = createVerifiedAccount();
        return new Account(id, username, account.passwordHash(), account.email(), account.tcAccepted(), status);
    }
}
