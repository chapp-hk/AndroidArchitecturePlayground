package app.ch.domain.weather.usecase

import app.ch.domain.di.IoDispatcher
import app.ch.domain.weather.repository.IWeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteWeatherUseCase @Inject
constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val weatherRepository: IWeatherRepository,
) {

    operator fun invoke(id: Long): Flow<Int> {
        return weatherRepository.deleteWeather(id)
            .flowOn(ioDispatcher)
    }
}
