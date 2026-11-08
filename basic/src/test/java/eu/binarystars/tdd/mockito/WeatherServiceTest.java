package eu.binarystars.tdd.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {
    // alternative w/out extension: weatherApi = mock(WeatherApi.class);
    @Mock
    WeatherApi weatherApi;
    @Mock
    AlertSender alertSender;
    WeatherService service;

    @BeforeEach
    void setUp() {
        service = new WeatherService(weatherApi, alertSender);
    }

    @Test
    void itShouldReportTheTemperature() {
        // given
        when(weatherApi.getTemperature("Berlin")).thenReturn(21);

        // when
        var actual = service.report("Berlin");

        // then
        assertThat(actual).isEqualTo("Berlin: 21°C");
    }

    @Test
    void itShouldThrowIfTheApiFails() {
        // given
        when(weatherApi.getTemperature("Berlin")).thenThrow(new WeatherApiException("timeout"));

        // then
        assertThatThrownBy(() -> service.report("Berlin"))
                .isExactlyInstanceOf(WeatherServiceException.class)
                .hasMessage("no weather data for Berlin")
                .hasCauseInstanceOf(WeatherApiException.class);
    }

    @Test
    void itShouldSendAHeatAlertAt30Degrees() {
        // given
        when(weatherApi.getTemperature("Berlin")).thenReturn(30);

        // when
        service.checkHeat("Berlin");

        // then
        verify(alertSender).send("Heat alert for Berlin: 30°C");
    }

    @Test
    void itShouldNotSendAnAlertAt29Degrees() {
        // given
        when(weatherApi.getTemperature("Berlin")).thenReturn(29);

        // when
        service.checkHeat("Berlin");

        // then
        verify(alertSender, never()).send(any());
    }

    @Test
    void itShouldNotSendAnAlertIfTheApiFails() {
        // given
        when(weatherApi.getTemperature("Berlin")).thenThrow(new WeatherApiException("timeout"));

        // then
        assertThatThrownBy(() -> service.checkHeat("Berlin"))
                .isExactlyInstanceOf(WeatherServiceException.class);
        verifyNoInteractions(alertSender);
    }

    @Test
    void itShouldSendAHeatAlertAt30DegreesUsingFakes() {
        // given
        var alertSenderFake = new AlertSenderListImpl();
        var fakeService = new WeatherService(new WeatherApiMapImpl(Map.of("Berlin", 30)), alertSenderFake);

        // when
        fakeService.checkHeat("Berlin");

        // then: a STATE test
        assertThat(alertSenderFake.sent).containsExactly("Heat alert for Berlin: 30°C");
    }
}
