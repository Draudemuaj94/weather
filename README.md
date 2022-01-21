# Documentacion **WeatherForecast**

### ¿Qué has empezado implementando y por qué?

Antes de hacer nada, tenía que buscar información de los siguientes conceptos: 
 * TDD
 * SOLID
 * Refactoring, code smells y código limpio.
 * Streams, Lambdas y optionals

A continuación tenía que entender el código y la API.

Después de entender que supuestos existían en la aplicación, implemente los test.
 
### ¿Qué problemas te has encontrado al implementar los tests y cómo los has solventado?

Es la primera vez que programo un test para algo que no es mío y nunca había usado JUnit.

Una vez entiendes las posibles respuestas de la aplicación entonces es fácil crear los test.

Fueron muy útiles porque al refactorizar te recuerda que la aplicación responde como lo hacía antes,
si alguien utiliza esta aplicación podrá hacerlo igual después de la refactorización.
Lo que facilitaba saber distinguir entre sí lo que quería hacer era una refactorización o sería un cambio en la aplicación.

Comente en los test para que no fuera necesario ver el resto del código para entender el supuesto caso que testeaba.

No se controla la posibilidad de que se llame a la función con el nombre de una ciudad inexistente, así que no hice su test.

```java
@Test
public void noCity_test() throws IOException {
WeatherForecast weatherForecast = new WeatherForecast();
String forecast = weatherForecast.getCityWeather("aaaa", new Date());
assertEquals("", forecast);
}
```

Aquí entraría avisar de que el código a refactorizar no está completo si se supone que debe controlar esta excepción.

En este caso es una solución sencilla que con una simple comprobación sé solucionaría el problema.
Pero puede ser perfectamente que en vez de implementar esa verificación,
decidamos que funcionaremos directamente con los Where On Earth ID (woeId).
De forma que al usuario le demos una lista de los nombres de ciudad relacionada con sus woeID y no tener que hacer su búsqueda cada vez.

Antes de implementar la solución y su test debería saber si se supone que tiene que controlar esa opción o si hay tiempo para hacerlo.

Como este caso también existe el caso de que se pase una fecha anterior a hoy.
Pero puestos así terminaríamos teniendo una aplicación a prueba de usuarios.

En resumen, sin contexto no debería implementar soluciones y por ende sus test.

### ¿Qué componentes has creado y por qué?

Implemente 3 funciones.
Dos de ellas son que dado un String te hace la búsqueda en la API y te devuelve el resultado.

`getWhereOnEarthIdByCity(String city)`
Como dice su nombre dado un nombre de ciudad te da un ID, deje woeId porque tanto en esta aplicación como API se usa el nombre y así seguimos la misma nomenclatura.
Convertí la fase de la aplicación en una función para simplificar el entendimiento de la aplicación, pasando solo un parámetro y hacer entender la función de un vistazo.

`getWeatherForecastById(String woeId)`
Devuelve el json en Array con las predicciones de la zona pedida.
La razón es la misma que el anterior caso,
pero fue necesario que devolviera el json array porque luego trabajaremos con él.

`forecastDaysLimit()`
Devolverá el día límite de predicción, es decir el día de hoy más 6 días.
Eso es debido a que ese es el máximo de predicciones que da la API a sus predicciones.
Si hay algo que necesita un comentario es mejor hacer una función que describa con una oración lo que hace.
De este modo me dio la sensación de que era más fácil de entender.

### Si has utilizado dependencias externas, ¿por qué has escogido esas dependencias?

No he incluido ninguna librería, pese a que son funcionales.

Esta solución era muy pequeña y al refactorizar no se deberían cambiar las dependencias o librerías.

Una vez hecha la refactorización sí que podría cambiar tipos de datos o librerías para trabajar mejor con otros proyectos de la empresa.
Por desgracia no conozco cuáles prefiere la empresa por encima de otros.

Podría no usar `Date()` porque al final usamos la fecha,
pero si la aplicación debe recibir un objeto 'Date()' ese tipo de dato se debe mantener hasta que un superior me dijera que puedo cambiarlo ahí.
También tendríamos la opción de sobreescribir la función con otro tipo de dato y dejar ambas opciones.
Pero muy probablemente la antigua llamaría a la nueva, pero cambiando al otro tipo de dato y así no duplicaríamos código.

### ¿Has utilizado  streams, lambdas y optionals de Java 8? ¿Qué te parece la programación funcional?

La programación funcional me gusta, los optionals te permiten trabajar con nulls.

Pero al final me dio la sensación de que debería reprogramarse con esas pautas,
es decir sería más una mejora que una refactorización.

### ¿Qué piensas del rendimiento de la aplicación? 

No sé que aplicaciones o en que situaciones se usara la aplicación.
Sí es para pocos casos y no muy repetitivo la aplicación tiene poco coste de desarrollo.
Si se quiere saber más de un día se debería utilizar la función por cada día,
lo cual no es óptimo porque en la API te obliga a pedir la predicción de varios días en vez de un día en concreto.

La API no me convence haciendo una simple comparación con otras puede que esta opción ya no sea la mejor.
Teniendo en cuenta la información que tiene API, podría dar más opciones mejor adaptadas a las posibles usos.

### ¿Qué harías para mejorar el rendimiento si esta aplicación fuera a recibir al menos 100 peticiones por segundo?

La aplicación que llame esta función usaría hilos `thread` para poder hacer más de una petición rest a la vez.
Como comente arriba podría utilizar una tabla con los datos de los woeId para que en vez de pedir a través de nombre lo hiciera por ID.
Así ahorrándonos la mitad de peticiones.
Seguramente tendría un método para obtener las predicciones de la zona sin filtrar el día.

De este modo la aplicación que emplea nuestra función no tendría que hacer múltiples peticiones para obtener varios días.

Ejemplo incompleto:

```java
public List<Optional<String>> getWeekWeather(String idCity) throws IOException {
        HttpRequestFactory rf = new NetHttpTransport().createRequestFactory();
        HttpRequest req = rf.buildGetRequest(new GenericUrl("https://www.metaweather.com/api/location/" + idCity));
        String r = req.execute().parseAsString();
        JSONArray results = new JSONObject(r).getJSONArray("consolidated_weather");
        List<Optional<String>> ArrayListWeather = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            ArrayListWeather.add(Optional.of(results.getJSONObject(i).get("weather_state_name").toString()));
        }
        return ArrayListWeather;
    }
return ArrayListWeather;
}
```

Luego conociendo el contexto podríamos implementar más soluciones.

### ¿Cuánto tiempo has invertido para implementar la solución? 

Estuve 4 horas a entender la aplicación, la API, hacer los test, refactorizar y comentar.
Estoy seguro de que puedo mejorar el tiempo, pero quería entender todo de la aplicación antes de ponerme a tocar código.
Esta versión esta subida en la [primera rama del proyecto](https://github.com/Draudemuaj94/weather/tree/PrimeraRefactorizacion).

Luego me puse a documentar y a revisar si de verdad hay algo que se me paso de alto.
[Refactorizacion Final](https://github.com/Draudemuaj94/weather/tree/RefactorizadoFinal).

En la rama [Maestra](https://github.com/Draudemuaj94/weather) subire la version con la refactorizacion final.
Ahí podría subir cambios si de alguna de las dudas que he tenido es resuelta y debería implementar algún cambio al código.

### ¿Crees que en un escenario real valdría la pena dedicar tiempo a realizar esta refactorización?

Siempre dependerá del uso que se le dé.
 * Veces que se usa
 * Puede fallar sin afectar a quienes lo usan
 * Cuantos programas lo usaran
 * Tiene potencial de crecimiento

Evidentemente, si queremos usarlo en futuros casos estaría bien hacer una refactorización.
De lo contrario con lo breve que es puede que se termine haciendo de nuevo con otras decisiones y siguiendo las buenas prácticas.

>Documentacion por Jaume Garau Pons