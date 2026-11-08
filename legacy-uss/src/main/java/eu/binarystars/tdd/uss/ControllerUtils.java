package eu.binarystars.tdd.uss;

import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ControllerUtils {
    static final String MESSAGE_SERVER_ERROR = "try again later";

    // the errors that are the client's fault, with their HTTP status
    static final Map<Class<? extends Exception>, Integer> CLIENT_ERRORS = Map.of(
            ValidationException.class, 400,
            AuthenticationException.class, 401,
            AccountNotVerifiedException.class, 400
    );

    private static final Logger LOG = Logger.getLogger(ControllerUtils.class.getName());

    private ControllerUtils() {
    }

    public static int calcHttpErrorCode(final Exception e) {
        return CLIENT_ERRORS.getOrDefault(e.getClass(), 500);
    }

    /**
     * Clients get the details of their own errors only, never the server's internals.
     */
    public static String calcErrorMessage(final Exception e) {
        return calcHttpErrorCode(e) < 500 ? e.getMessage() : MESSAGE_SERVER_ERROR;
    }

    /**
     * Runs a service call and maps its outcome to the HTTP result:
     * the result with the given status, or an error status with a message.
     */
    public static HttpResult respond(final int successStatus, final Supplier<Object> serviceCall) {
        try {
            return new HttpResult(successStatus, serviceCall.get());
        } catch (Exception e) {
            final int status = calcHttpErrorCode(e);
            if (status >= 500) {
                LOG.log(Level.SEVERE, "server error", e);
            }
            return new HttpResult(status, Map.of("error", calcErrorMessage(e)));
        }
    }
}
