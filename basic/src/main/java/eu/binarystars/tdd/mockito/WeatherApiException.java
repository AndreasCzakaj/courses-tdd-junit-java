package eu.binarystars.tdd.mockito;

public class WeatherApiException extends RuntimeException {
    public WeatherApiException(final String message) {
        super(message);
    }
}
