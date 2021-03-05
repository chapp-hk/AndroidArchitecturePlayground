package app.ch.weatherapp.weather

import app.ch.base.recyclerview.ListItem
import app.ch.domain.weather.entity.WeatherEntity
import app.ch.weatherapp.R

data class WeatherConditionListItem(
    val iconUrl: String,
    val description: String,
    override val layoutId: Int = R.layout.item_weather_condition,
) : ListItem

fun WeatherEntity.Condition.toListItem(): WeatherConditionListItem {
    return WeatherConditionListItem(
        iconUrl = iconUrl,
        description = description
    )
}
