package app.ch.weatherapp.history

import app.ch.base.recyclerview.ListItem
import app.ch.domain.weather.entity.WeatherEntity
import app.ch.weatherapp.R

data class HistoryListItem(
    val cityName: String,
    val coorLat: String,
    val coorLong: String,
    override val layoutId: Int = R.layout.item_weather_history
) : ListItem

fun WeatherEntity.toUiModel(): HistoryListItem {
    return HistoryListItem(
        cityName = this.name,
        coorLat = this.coordLat.toString(),
        coorLong = this.coordLon.toString(),
    )
}
