package eu.binarystars.tdd.mockito;

/**
 * Port to an external weather service (HTTP, rate limited, not deterministic).
 * There is no real implementation in this repo => perfect for test doubles.
 */
public interface WeatherApi {
    int getTemperature(String city) throws WeatherApiException;
}
