package app.ch.data.weather.repository

import app.ch.data.weather.local.WeatherLocalDataSource
import app.ch.data.weather.mapper.toDomainEntity
import app.ch.data.weather.remote.WeatherRemoteDataSource
import app.ch.domain.weather.entity.WeatherEntity
import app.ch.domain.weather.repository.IWeatherRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class WeatherRepository @Inject
constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource,
) : IWeatherRepository {

    override suspend fun getWeatherByCityName(cityName: String): Flow<WeatherEntity> {
        return remoteDataSource.getWeatherByCityName(cityName)
            .onEach {
                localDataSource.insertWeather(it)
            }
            .map {
                it.toDomainEntity()
            }
    }

    override suspend fun getWeatherHistory(): Flow<List<WeatherEntity>> {
        return flow {
            localDataSource.getWeatherHistory()
                .single()
                .map {
                    it.toDomainEntity()
                }.let {
                    emit(it)
                }
        }
    }
}
