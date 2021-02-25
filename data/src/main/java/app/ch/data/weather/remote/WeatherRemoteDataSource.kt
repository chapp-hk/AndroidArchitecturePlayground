package app.ch.data.weather.remote

import app.ch.data.weather.mapper.toDataModel
import app.ch.data.weather.model.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRemoteDataSource @Inject
constructor(
    private val weatherApi: WeatherApi,
) {

    suspend fun getWeatherByCityName(cityName: String): Flow<WeatherModel> {
        return flow {
            weatherApi.getWeatherByCityName(cityName)
                .toDataModel()
                .let {
                    emit(it)
                }
        }
    }
}
