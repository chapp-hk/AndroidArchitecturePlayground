package app.ch.domain.weather.entity

data class WeatherEntity(
    val id: Long,
    val name: String,
    val coordLat: Double,
    val coordLon: Double,
    val conditions: List<Condition>,
    val temperature: Double,
    val feelsLike: Double,
    val temperatureMin: Double,
    val temperatureMax: Double,
    val pressure: Int,
    val humidity: Int,
    val visibility: Int,
    val windSpeed: Double,
    val windDeg: Int,
    val cloudiness: Int,
) {

    data class Condition(
        val id: Long,
        val main: String,
        val description: String,
        val iconUrl: String,
    )
}
