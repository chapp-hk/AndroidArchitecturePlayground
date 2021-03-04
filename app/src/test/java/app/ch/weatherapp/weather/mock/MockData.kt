package app.ch.weatherapp.weather.mock

import app.ch.domain.weather.entity.WeatherEntity

object MockData {

    val conditionEntity = WeatherEntity.Condition(
        id = 880,
        main = "clear",
        description = "clear sky",
        iconUrl = "89",
    )

    val weatherEntity = WeatherEntity(
        id = 879,
        name = "Hong Kong",
        coordLat = 98.0,
        coordLon = 768.327,
        conditions = listOf(conditionEntity),
        temperature = 89.8,
        feelsLike = 6578.9,
        temperatureMin = 6273.0,
        temperatureMax = 65723.8,
        pressure = 78,
        humidity = 678,
        visibility = 0,
        windSpeed = 6578.2,
        windDeg = 7,
        cloudiness = 6,
    )
}
