package eu.binarystars.tdd.uss;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Pure functions: they return the valid value, so the caller gets it with the right type.
 */
public final class Validation {
    static final Pattern REGEX_USERNAME = Pattern.compile("[a-zA-Z0-9\\-_]{8,20}");
    static final Pattern REGEX_PASSWORD = Pattern.compile("[a-zA-Z0-9\\-_.,+]{12,32}");

    private Validation() {
    }

    public static String validateUsername(final Object username) {
        return validate(username, REGEX_USERNAME, "username");
    }

    public static String validatePassword(final Object password) {
        return validate(password, REGEX_PASSWORD, "password");
    }

    /**
     * @param body untrusted input: may be anything, e.g. the parsed body of an HTTP request
     */
    public static Credentials validateCredentials(final Object body) {
        final Map<?, ?> data = body instanceof Map<?, ?> map ? map : Map.of();
        return new Credentials(
                validateUsername(data.get("username")),
                validatePassword(data.get("password"))
        );
    }

    private static String validate(final Object value, final Pattern regex, final String field) {
        if (value instanceof String string && regex.matcher(string).matches()) {
            return string;
        }
        throw new ValidationException(field);
    }
}
