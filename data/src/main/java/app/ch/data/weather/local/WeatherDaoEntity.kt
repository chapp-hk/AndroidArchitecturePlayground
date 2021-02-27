package app.ch.data.weather.local

import androidx.room.*

@Entity(tableName = "weather")
data class WeatherDaoEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "coord_lat")
    val coordLat: Double,

    @ColumnInfo(name = "coord_lon")
    val coordLon: Double,

    @ColumnInfo(name = "temperature")
    val temperature: Double,

    @ColumnInfo(name = "feels_like")
    val feelsLike: Double,

    @ColumnInfo(name = "temperature_min")
    val temperatureMin: Double,

    @ColumnInfo(name = "temperature_max")
    val temperatureMax: Double,

    @ColumnInfo(name = "pressure")
    val pressure: Int,

    @ColumnInfo(name = "humidity")
    val humidity: Int,

    @ColumnInfo(name = "visibility")
    val visibility: Int,

    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double,

    @ColumnInfo(name = "wind_deg")
    val windDeg: Int,

    @ColumnInfo(name = "cloudiness")
    val cloudiness: Int,

    @ColumnInfo(name = "create_time")
    val createTime: Long = System.currentTimeMillis(),
)

@Entity(
    tableName = "condition",
    foreignKeys = [
        ForeignKey(
            entity = WeatherDaoEntity::class,
            parentColumns = ["id"],
            childColumns = ["weather_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["id", "weather_id"],
    indices = [
        Index("weather_id"),
    ]
)
data class ConditionDaoEntity(

    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "main")
    val main: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "icon")
    val icon: String,

    @ColumnInfo(name = "weather_id")
    val weatherId: Long,
)

data class WeatherWithConditions(

    @Embedded
    val weather: WeatherDaoEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "weather_id"
    )
    val conditions: List<ConditionDaoEntity>
)
