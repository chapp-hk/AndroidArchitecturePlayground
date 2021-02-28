package app.ch.weatherapp.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.ch.domain.weather.entity.WeatherEntity
import app.ch.domain.weather.usecase.GetWeatherHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject
constructor(
    private val getWeatherHistory: GetWeatherHistoryUseCase,
) : ViewModel() {

    fun queryWeatherHistory(): Flow<PagingData<WeatherEntity>> {
        return getWeatherHistory().cachedIn(viewModelScope)
    }
}
