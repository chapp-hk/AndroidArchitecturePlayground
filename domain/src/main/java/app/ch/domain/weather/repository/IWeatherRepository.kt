package app.ch.domain.weather.repository

import androidx.paging.PagingData
import app.ch.domain.weather.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {

    fun getWeatherByCityName(cityName: String): Flow<WeatherEntity>

    fun getWeatherByLocation(lat: Double, lon: Double): Flow<WeatherEntity>

    fun getWeatherHistory(): Flow<PagingData<WeatherEntity>>

    fun getLatestSearchedWeather(): Flow<WeatherEntity>

    fun deleteWeather(id: Long): Flow<Int>

    fun deleteAllWeather(): Flow<Unit>
}
