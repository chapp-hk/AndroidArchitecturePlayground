package app.ch.data.weather.local

import app.ch.data.weather.mapper.toDaoEntity
import app.ch.data.weather.mapper.toDataModel
import app.ch.data.weather.model.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherLocalDataSource @Inject
constructor(
    private val weatherDao: WeatherDao,
) {

    suspend fun insertWeather(weather: WeatherModel) {
        weatherDao.apply {
            insertWeather(weather.toDaoEntity())
            insertAllConditions(weather.conditions.map { it.toDaoEntity() })
        }
    }

    suspend fun getWeatherHistory(): Flow<List<WeatherModel>> {
        return flow {
            weatherDao.getWeathers()
                .map {
                    it.toDataModel()
                }.let {
                    emit(it)
                }
        }
    }
}
