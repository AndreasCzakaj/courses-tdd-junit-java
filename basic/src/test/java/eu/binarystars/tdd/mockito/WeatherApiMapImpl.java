package eu.binarystars.tdd.mockito;

import java.util.Map;

/**
 * Fake: real code, in-memory, deterministic.
 */
public class WeatherApiMapImpl implements WeatherApi {
    private final Map<String, Integer> temperatures;

    public WeatherApiMapImpl(final Map<String, Integer> temperatures) {
        this.temperatures = temperatures;
    }

    @Override
    public int getTemperature(final String city) {
        final Integer temperature = temperatures.get(city);
        if (temperature == null) {
            throw new WeatherApiException("unknown city: " + city);
        }
        return temperature;
    }
}
