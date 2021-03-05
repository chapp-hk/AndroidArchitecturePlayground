package app.ch.domain.weather.usecase

import app.ch.domain.base.CoroutineDispatcherProvider
import app.ch.domain.weather.repository.IWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteWeatherUseCase @Inject
constructor(
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val weatherRepository: IWeatherRepository,
) {

    operator fun invoke(id: Long): Flow<Int> {
        return weatherRepository.deleteWeather(id)
            .flowOn(dispatcherProvider.ioDispatcher)
    }
}
