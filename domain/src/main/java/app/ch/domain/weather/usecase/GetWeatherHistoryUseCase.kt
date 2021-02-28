package app.ch.domain.weather.usecase

import androidx.paging.PagingData
import app.ch.domain.weather.entity.WeatherEntity
import app.ch.domain.weather.repository.IWeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherHistoryUseCase @Inject
constructor(
    private val weatherRepository: IWeatherRepository,
) {

    operator fun invoke(): Flow<PagingData<WeatherEntity>> {
        return weatherRepository.getWeatherHistory()
    }
}
