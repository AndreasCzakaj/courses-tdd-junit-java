package eu.binarystars.tdd.mockito;

import java.util.HashMap;
import java.util.Map;

/**
 * Decorator: asks the wrapped API only once per city.
 */
public class CachingWeatherApi implements WeatherApi {
    private final WeatherApi delegate;
    private final Map<String, Integer> cache = new HashMap<>();

    public CachingWeatherApi(final WeatherApi delegate) {
        this.delegate = delegate;
    }

    @Override
    public int getTemperature(final String city) {
        return cache.computeIfAbsent(city, delegate::getTemperature);
    }
}
