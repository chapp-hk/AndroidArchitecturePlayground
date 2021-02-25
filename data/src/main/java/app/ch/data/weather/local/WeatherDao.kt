package app.ch.data.weather.local

import androidx.room.*

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherDaoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllConditions(conditions: List<ConditionDaoEntity>)

    @Transaction
    @Query("SELECT * FROM weather")
    suspend fun getWeathers(): List<WeatherWithConditions>
}
