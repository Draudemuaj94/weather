package training.weather;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherForecast {

    /**
     * Devolvera la predicion del dia pedido.
     *
     * @param city
     * @param datetime
     * @return
     * @throws IOException
     */
    public String getCityWeather(String city, Date datetime) throws IOException {
        if (datetime == null) {
            datetime = new Date();
        }
        if (datetime.before(forecastDaysLimit())) {
            String woeId = getWhereOnEarthIdByCity(city);
            JSONArray WeatherForecast = getWeatherForecastById(woeId);
            String datetimeFormat = new SimpleDateFormat("yyyy-MM-dd").format(datetime);
            for (int i = 0; i < WeatherForecast.length(); i++) {
                if (datetimeFormat.equals(WeatherForecast.getJSONObject(i).get("applicable_date").toString())) {
                    return WeatherForecast.getJSONObject(i).get("weather_state_name").toString();
                }
            }
        }
        return "";
    }

    /**
     * Devuelve la fecha de ejecucion mas 6 dias. 518.400.000 son 6 dias en
     * milisegundo.
     *
     * @return
     */
    public Date forecastDaysLimit() {
        return new Date(new Date().getTime() + (518400000));
    }

    public String getWhereOnEarthIdByCity(String city) throws IOException {
        HttpRequestFactory rf = new NetHttpTransport().createRequestFactory();
        HttpRequest req = rf.buildGetRequest(
                new GenericUrl("https://www.metaweather.com/api/location/search/?query=" + city)
        );
        String answer = req.execute().parseAsString();
        JSONArray json = new JSONArray(answer);
        String woeId = json.getJSONObject(0).get("woeid").toString();
        return woeId;
    }

    public JSONArray getWeatherForecastById(String woeId) throws IOException {
        HttpRequestFactory rf = new NetHttpTransport().createRequestFactory();
        HttpRequest req = rf.buildGetRequest(new GenericUrl("https://www.metaweather.com/api/location/" + woeId));
        String answer = req.execute().parseAsString();
        JSONArray results = new JSONObject(answer).getJSONArray("consolidated_weather");

        return results;
    }

}
