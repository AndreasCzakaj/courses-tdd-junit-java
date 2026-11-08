package eu.binarystars.tdd.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CachingWeatherApiTest {
    WeatherApi realApi;
    CachingWeatherApi cachingApi;

    @BeforeEach
    void setUp() {
        // spy: the REAL object (here: a fake), but every call is recorded
        realApi = spy(new WeatherApiMapImpl(Map.of("Berlin", 21, "Hamburg", 15)));
        cachingApi = new CachingWeatherApi(realApi);
    }

    @Test
    void itShouldYieldTheTemperatureOfTheRealApi() {
        assertThat(cachingApi.getTemperature("Berlin")).isEqualTo(21);
        assertThat(cachingApi.getTemperature("Hamburg")).isEqualTo(15);
    }

    @Test
    void itShouldCallTheRealApiOnlyOncePerCity() {
        // when
        cachingApi.getTemperature("Berlin");
        cachingApi.getTemperature("Berlin");

        // then
        verify(realApi, times(1)).getTemperature("Berlin");
    }

    @Test
    void itShouldCallTheRealApiForEachCity() {
        // when
        cachingApi.getTemperature("Berlin");
        cachingApi.getTemperature("Hamburg");
        cachingApi.getTemperature("Berlin");

        // then
        verify(realApi, times(1)).getTemperature("Berlin");
        verify(realApi, times(1)).getTemperature("Hamburg");
    }

    @Test
    void itShouldNotCacheErrors() {
        // when
        assertThatThrownBy(() -> cachingApi.getTemperature("Atlantis"))
                .isExactlyInstanceOf(WeatherApiException.class);
        assertThatThrownBy(() -> cachingApi.getTemperature("Atlantis"))
                .isExactlyInstanceOf(WeatherApiException.class);

        // then
        verify(realApi, times(2)).getTemperature("Atlantis");
    }
}
