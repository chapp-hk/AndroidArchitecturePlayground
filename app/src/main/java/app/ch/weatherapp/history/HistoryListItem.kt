package app.ch.weatherapp.history

import app.ch.base.recyclerview.ListItem
import app.ch.domain.weather.entity.WeatherEntity
import app.ch.weatherapp.R
import kotlinx.coroutines.flow.MutableStateFlow

data class HistoryListItem(
    val id: Long,
    val cityName: String,
    val coorLat: String,
    val coorLong: String,
    val deleteEvent: MutableStateFlow<Long>,
    override val layoutId: Int = R.layout.item_weather_history,
) : ListItem {

    fun delete() {
        deleteEvent.value = id
    }
}

fun WeatherEntity.toUiModel(deleteEvent: MutableStateFlow<Long>): HistoryListItem {
    return HistoryListItem(
        id = this.id,
        cityName = this.name,
        coorLat = this.coordLat.toString(),
        coorLong = this.coordLon.toString(),
        deleteEvent = deleteEvent
    )
}
