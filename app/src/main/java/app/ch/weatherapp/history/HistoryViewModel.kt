package app.ch.weatherapp.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import app.ch.domain.weather.usecase.DeleteAllWeatherUseCase
import app.ch.domain.weather.usecase.DeleteWeatherUseCase
import app.ch.domain.weather.usecase.GetWeatherHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject
constructor(
    private val getWeatherHistory: GetWeatherHistoryUseCase,
    private val deleteWeather: DeleteWeatherUseCase,
    private val deleteAllWeather: DeleteAllWeatherUseCase,
) : ViewModel() {

    private val _historyEvent = MutableSharedFlow<HistoryEvent>()
    val historyEvent = _historyEvent.asSharedFlow()

    fun queryWeatherHistory(): Flow<PagingData<HistoryListItem>> {
        return getWeatherHistory().map { pagingData ->
            pagingData.map {
                it.toUiModel(_historyEvent, viewModelScope)
            }
        }.cachedIn(viewModelScope)
    }

    fun deleteItem(id: Long) {
        deleteWeather(id).onEach {
            _historyEvent.emit(HistoryEvent.ListChanged)
        }.launchIn(viewModelScope)
    }

    fun deleteAllItems() {
        deleteAllWeather().onEach {
            _historyEvent.emit(HistoryEvent.ListChanged)
        }.launchIn(viewModelScope)
    }
}
