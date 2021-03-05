package app.ch.weatherapp.history

import app.ch.base.recyclerview.ListItem
import app.ch.domain.weather.entity.WeatherEntity
import app.ch.weatherapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

data class HistoryListItem(
    val id: Long,
    val cityName: String,
    val coorLat: String,
    val coorLong: String,
    val event: MutableSharedFlow<HistoryEvent>,
    val coroutineScope: CoroutineScope,
    override val layoutId: Int = R.layout.item_history,
) : ListItem {

    fun delete() {
        coroutineScope.launch {
            event.emit(HistoryEvent.DeleteItem(id))
        }
    }

    fun display() {
        coroutineScope.launch {
            event.emit(HistoryEvent.Display(cityName))
        }
    }
}

fun WeatherEntity.toUiModel(
    event: MutableSharedFlow<HistoryEvent>,
    coroutineScope: CoroutineScope,
): HistoryListItem {
    return HistoryListItem(
        id = this.id,
        cityName = this.name,
        coorLat = this.coordLat.toString(),
        coorLong = this.coordLon.toString(),
        event = event,
        coroutineScope = coroutineScope,
    )
}
