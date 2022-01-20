package training.weather;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import java.io.IOException;
import java.util.Date;
import org.junit.Test;

public class WeatherForecastTest {

    /**
     * Si hacemos la busqueda del mismo dia nos devolvera un string con
     * contenido.
     *
     * @throws IOException
     */
    @Test
    public void general_test() throws IOException {
        WeatherForecast weatherForecast = new WeatherForecast();
        String forecast = weatherForecast.getCityWeather("Madrid", new Date());
        assertNotSame("", forecast);
    }

    /**
     * Cuando pasamos el tiempo en null, nos da el tiempo de hoy.
     *
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
     * Si pasamos el limite de dias de pronostico no hace la busqueda.
     *
     * @throws IOException
     */
    @Test
    public void limitDay_test() throws IOException {
        WeatherForecast weatherForecast = new WeatherForecast();
        String forecast = weatherForecast.getCityWeather(
                "Madrid", weatherForecast.forecastDaysLimit()
        );
        assertEquals("", forecast);
    }

}
