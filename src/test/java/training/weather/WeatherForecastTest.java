package training.weather;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.junit.Test;

public class WeatherForecastTest {

    @Test
    public void unfinished_test() throws IOException {
        WeatherForecast weatherForecast = new WeatherForecast();
        String forecast = weatherForecast.getCityWeather("Madrid", new Date());
        System.out.println(forecast);
    }

    /**
     * Cuando pedimos el tiempo en null, nos da el tiempo de hoy.
     * @throws IOException 
     */
    @Test
    public void nullDay_test() throws IOException {
        WeatherForecast weatherForecast = new WeatherForecast();
        String forecastToday = weatherForecast.getCityWeather("Madrid", new Date());
        String forecastNull = weatherForecast.getCityWeather("Madrid", null);
        assertEquals(forecastToday, forecastNull);
    }

    /**
     * A partir de los 6 dias no hay previsiones, así que limitamos la busqueda.
     * @throws IOException 
     */
    @Test
    public void limitDay_test() throws IOException {
        WeatherForecast weatherForecast = new WeatherForecast();
        String forecast = weatherForecast.getCityWeather(
                "Madrid", new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 6))
        ); 
        assertEquals("", forecast);
    }

}
