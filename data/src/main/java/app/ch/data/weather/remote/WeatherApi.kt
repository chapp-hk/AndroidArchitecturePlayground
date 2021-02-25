package app.ch.data.weather.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherByCityName(@Query("q") query: String): WeatherResponse
}
