package app.ch.data.weather.repository

import androidx.paging.PagingData
import androidx.paging.map
import app.ch.data.weather.local.WeatherLocalDataSource
import app.ch.data.weather.mapper.toDomainEntity
import app.ch.data.weather.remote.WeatherRemoteDataSource
import app.ch.domain.weather.entity.WeatherEntity
import app.ch.domain.weather.repository.IWeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
class WeatherRepository @Inject
constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource,
) : IWeatherRepository {

    override fun getWeatherByCityName(cityName: String): Flow<WeatherEntity> {
        return remoteDataSource.getWeatherByCityName(cityName)
            .onEach {
                localDataSource.insertWeather(it)
            }
            .map {
                it.toDomainEntity()
            }
    }

    override fun getWeatherByLocation(lat: Double, lon: Double): Flow<WeatherEntity> {
        return remoteDataSource.getWeatherByLocation(lat, lon)
            .onEach {
                localDataSource.insertWeather(it)
            }
            .map {
                it.toDomainEntity()
            }
    }

    override fun getWeatherHistory(): Flow<PagingData<WeatherEntity>> {
        return localDataSource.getWeatherHistory()
            .map { pagingData ->
                pagingData.map {
                    it.toDomainEntity()
                }
            }
    }

    override fun getLatestSearchedWeather(): Flow<WeatherEntity> {
        return localDataSource.getLatestWeather()
            .map {
                it.toDomainEntity()
            }
    }
}
