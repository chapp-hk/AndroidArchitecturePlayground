package app.ch.domain.weather.repository

import androidx.paging.PagingData
import app.ch.domain.weather.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {

    suspend fun getWeatherByCityName(cityName: String): Flow<WeatherEntity>

    suspend fun getWeatherByLocation(lat: Double, lon: Double): Flow<WeatherEntity>

    fun getWeatherHistory(): Flow<PagingData<WeatherEntity>>
}
