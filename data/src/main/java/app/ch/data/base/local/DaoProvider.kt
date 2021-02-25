package app.ch.data.base.local

import androidx.room.Database
import androidx.room.RoomDatabase
import app.ch.data.weather.local.ConditionDaoEntity
import app.ch.data.weather.local.WeatherDao
import app.ch.data.weather.local.WeatherDaoEntity

@Database(
    entities = [
        WeatherDaoEntity::class,
        ConditionDaoEntity::class,
    ],
    version = 1
)
abstract class DaoProvider : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDao
}
