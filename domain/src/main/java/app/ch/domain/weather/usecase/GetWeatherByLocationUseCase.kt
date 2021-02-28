package app.ch.domain.weather.usecase

import app.ch.domain.di.IoDispatcher
import app.ch.domain.weather.entity.WeatherEntity
import app.ch.domain.weather.repository.IWeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetWeatherByLocationUseCase @Inject
constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val weatherRepository: IWeatherRepository,
) {

    operator fun invoke(lat: Double, lon: Double): Flow<WeatherEntity> {
        return weatherRepository.getWeatherByLocation(lat, lon)
            .flowOn(ioDispatcher)
    }
}
