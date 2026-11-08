package eu.binarystars.tdd.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CachingWeatherApiTest {
    WeatherApi realApi;
    CachingWeatherApi cachingApi;

    @BeforeEach
    void setUp() {
        realApi = new WeatherApiMapImpl(Map.of("Berlin", 21, "Hamburg", 15));
        // TODO: wrap realApi in a spy, so you can verify the calls
        cachingApi = new CachingWeatherApi(realApi);
    }

    // no test double needed: a state test
    @Test
    @Disabled("it should yield 21 for Berlin and 15 for Hamburg")
    void itShouldYieldTheTemperatureOfTheRealApi() {
    }

    // spy & verify: verify(..., times(1))
    @Test
    @Disabled("it should call the real API only once if asked twice for Berlin")
    void itShouldCallTheRealApiOnlyOncePerCity() {
    }

    @Test
    @Disabled("it should call the real API once for Berlin and once for Hamburg")
    void itShouldCallTheRealApiForEachCity() {
    }

    @Test
    @Disabled("it should NOT cache errors: asking twice for 'Atlantis' should call the real API twice")
    void itShouldNotCacheErrors() {
    }
}
