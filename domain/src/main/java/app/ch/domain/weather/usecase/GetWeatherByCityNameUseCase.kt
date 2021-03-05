package app.ch.domain.weather.usecase

import app.ch.domain.base.CoroutineDispatcherProvider
import app.ch.domain.weather.entity.WeatherEntity
import app.ch.domain.weather.repository.IWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetWeatherByCityNameUseCase @Inject
constructor(
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val weatherRepository: IWeatherRepository,
) {

    operator fun invoke(cityName: String): Flow<WeatherEntity> {
        return weatherRepository.getWeatherByCityName(cityName)
            .flowOn(dispatcherProvider.ioDispatcher)
    }
}
