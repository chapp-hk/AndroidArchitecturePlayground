package app.ch.data.weather.mock

import app.ch.data.weather.local.ConditionDaoEntity
import app.ch.data.weather.local.WeatherDaoEntity

object MockData {
    val weather = WeatherDaoEntity(
        id = 721831,
        name = "Hong Kong",
        coordLat = Double.MAX_VALUE,
        coordLon = Double.MIN_VALUE,
        temperature = 1.2,
        feelsLike = 2.3,
        temperatureMin = -9.0,
        temperatureMax = 2.0,
        pressure = 423,
        humidity = 78,
        visibility = 8,
        windSpeed = 0.0,
        windDeg = 12,
        cloudiness = 2,
    )

    val conditions = listOf(
        ConditionDaoEntity(
            id = 8964,
            main = "Clear",
            description = "Clear sky",
            icon = "this.icon",
            weatherId = weather.id,
        )
    )
}
