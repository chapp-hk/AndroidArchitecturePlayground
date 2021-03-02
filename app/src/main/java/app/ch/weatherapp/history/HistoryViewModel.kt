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

    private val _deleteItemEvent = MutableStateFlow(Long.MIN_VALUE)
    val deleteItemEvent = _deleteItemEvent.asSharedFlow()

    private val _deleteAllItemsEvent = MutableSharedFlow<Unit>()
    val deleteAllItemsEvent = _deleteAllItemsEvent.asSharedFlow()

    fun queryWeatherHistory(): Flow<PagingData<HistoryListItem>> {
        return getWeatherHistory().map { pagingData ->
            pagingData.map {
                it.toUiModel(_deleteItemEvent)
            }
        }.cachedIn(viewModelScope)
    }

    fun deleteItem(id: Long) {
        deleteWeather(id).launchIn(viewModelScope)
    }

    fun deleteAllItems() {
        deleteAllWeather().onEach {
            _deleteAllItemsEvent.emit(Unit)
        }.launchIn(viewModelScope)
    }
}
