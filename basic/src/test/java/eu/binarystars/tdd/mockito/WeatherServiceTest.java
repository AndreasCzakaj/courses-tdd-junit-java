package eu.binarystars.tdd.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WeatherServiceTest {
    WeatherApi weatherApi;
    AlertSender alertSender;
    WeatherService service;

    @BeforeEach
    void setUp() {
        // TODO: weatherApi & alertSender are null => replace them by mocks
        service = new WeatherService(weatherApi, alertSender);
    }

    // mock a result: when(...).thenReturn(...)
    @Test
    @Disabled("report should yield 'Berlin: 21°C' if the API yields 21 for Berlin")
    void itShouldReportTheTemperature() {
    }

    // mock an exception: when(...).thenThrow(...)
    @Test
    @Disabled("report should throw a WeatherServiceException 'no weather data for Berlin' if the API throws a WeatherApiException")
    void itShouldThrowIfTheApiFails() {
    }

    // verify a call: verify(...).send(...)
    @Test
    @Disabled("checkHeat should send 'Heat alert for Berlin: 30°C' if the API yields 30 for Berlin")
    void itShouldSendAHeatAlertAt30Degrees() {
    }

    // verify that there was NO call: verify(..., never()) or verifyNoInteractions(...)
    @Test
    @Disabled("checkHeat should not send any alert if the API yields 29 for Berlin")
    void itShouldNotSendAnAlertAt29Degrees() {
    }

    @Test
    @Disabled("checkHeat should not send any alert if the API fails, but throw a WeatherServiceException")
    void itShouldNotSendAnAlertIfTheApiFails() {
    }

    // Bonus: do it again WITHOUT Mockito => write a fake AlertSender that collects the messages in a list
    @Test
    @Disabled("Bonus: checkHeat should send 'Heat alert for Berlin: 30°C' ... using fakes only")
    void itShouldSendAHeatAlertAt30DegreesUsingFakes() {
    }
}
