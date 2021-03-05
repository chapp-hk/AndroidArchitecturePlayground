package app.ch.domain.weather.usecase

import app.ch.domain.base.CoroutineDispatcherProvider
import app.ch.domain.weather.repository.IWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteAllWeatherUseCase @Inject
constructor(
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val weatherRepository: IWeatherRepository,
) {

    operator fun invoke(): Flow<Unit> {
        return weatherRepository.deleteAllWeather()
            .flowOn(dispatcherProvider.ioDispatcher)
    }
}
