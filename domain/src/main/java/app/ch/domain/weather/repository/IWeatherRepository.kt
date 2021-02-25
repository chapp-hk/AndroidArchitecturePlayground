package app.ch.domain.weather.repository

import app.ch.domain.weather.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {

    suspend fun getWeatherByCityName(cityName: String): Flow<WeatherEntity>

    suspend fun getWeatherHistory(): Flow<List<WeatherEntity>>
}
