package app.ch.weatherapp.weather

import app.ch.domain.base.ErrorEntity

sealed class WeatherEvent {

    object StartSearch : WeatherEvent()

    class Error(val error: ErrorEntity) : WeatherEvent()
}
