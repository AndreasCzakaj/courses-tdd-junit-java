package eu.binarystars.tdd.mockito;

public class WeatherService {
    static final int HEAT_THRESHOLD = 30;

    private final WeatherApi weatherApi;
    private final AlertSender alertSender;

    public WeatherService(final WeatherApi weatherApi, final AlertSender alertSender) {
        this.weatherApi = weatherApi;
        this.alertSender = alertSender;
    }

    public String report(final String city) {
        return city + ": " + loadTemperature(city) + "°C";
    }

    public void checkHeat(final String city) {
        final int temperature = loadTemperature(city);
        if (temperature >= HEAT_THRESHOLD) {
            alertSender.send("Heat alert for " + city + ": " + temperature + "°C");
        }
    }

    int loadTemperature(final String city) {
        try {
            return weatherApi.getTemperature(city);
        } catch (WeatherApiException e) {
            throw new WeatherServiceException("no weather data for " + city, e);
        }
    }
}
