package app.ch.data.weather.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(

    @SerialName("id")
    val id: Long? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("coord")
    val coord: Coord? = null,

    @SerialName("weather")
    val conditions: List<Condition> = listOf(),

    @SerialName("main")
    val main: Main? = null,

    /**
     * Visibility, meter
     */
    @SerialName("visibility")
    val visibility: Int? = null,

    @SerialName("wind")
    val wind: Wind? = null,

    @SerialName("clouds")
    val clouds: Clouds? = null,
) {

    @Serializable
    data class Coord(

        /**
         * City geo location, longitude
         */
        @SerialName("lat")
        val lat: Double,

        /**
         * City geo location, longitude
         */
        @SerialName("lon")
        val lon: Double,
    )

    /**
     * more info Weather condition codes
     */
    @Serializable
    data class Condition(

        /**
         * Weather condition id
         */
        @SerialName("id")
        val id: Long,

        /**
         * Group of weather parameters (Rain, Snow, Extreme etc.)
         */
        @SerialName("main")
        val main: String,

        /**
         * Weather condition within the group. You can get the output in your language.
         */
        @SerialName("description")
        val description: String,

        /**
         * Weather icon id
         * convert to url here
         */
        @Serializable(with = IconUrlSerializer::class)
        @SerialName("icon")
        val iconUrl: String,
    )

    @Serializable
    data class Main(

        /**
         * Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
         */
        @SerialName("temp")
        val temp: Double,

        /**
         * Temperature. This temperature parameter accounts for the human perception of weather.
         * Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
         */
        @SerialName("feels_like")
        val feelsLike: Double,

        /**
         * Minimum temperature at the moment.
         * This is minimal currently observed temperature (within large megalopolises and urban areas).
         * Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
         */
        @SerialName("temp_min")
        val tempMin: Double,

        /**
         * Maximum temperature at the moment.
         * This is maximal currently observed temperature (within large megalopolises and urban areas).
         * Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
         */
        @SerialName("temp_max")
        val tempMax: Double,

        /**
         * Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
         */
        @SerialName("pressure")
        val pressure: Int,

        /**
         * Humidity, %
         */
        @SerialName("humidity")
        val humidity: Int,
    )

    @Serializable
    data class Wind(

        /**
         * Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
         */
        @SerialName("speed")
        val speed: Double,

        /**
         * Wind direction, degrees (meteorological)
         */
        @SerialName("deg")
        val deg: Int,
    )

    @Serializable
    data class Clouds(

        /**
         * Cloudiness, %
         */
        @SerialName("all")
        val all: Int,
    )
}
