package eu.binarystars.tdd.mockito;

public class WeatherServiceException extends RuntimeException {
    public WeatherServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
