package app.ch.domain.weather.usecase

import app.ch.domain.di.IoDispatcher
import app.ch.domain.di.MainDispatcher
import app.ch.domain.weather.entity.WeatherEntity
import app.ch.domain.weather.repository.IWeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherHistoryUseCase @Inject
constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val weatherRepository: IWeatherRepository,
) {

    @ExperimentalCoroutinesApi
    suspend operator fun invoke(): Flow<List<WeatherEntity>> {
        return withContext(mainDispatcher) {
            weatherRepository.getWeatherHistory()
                .flowOn(ioDispatcher)
        }
    }
}
