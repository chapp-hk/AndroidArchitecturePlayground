package app.ch.data.weather.local

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: WeatherDaoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllConditions(conditions: List<ConditionDaoEntity>)

    @Transaction
    @Query("SELECT * FROM weather ORDER BY create_time DESC")
    fun getWeathers(): PagingSource<Int, WeatherWithConditions>

    @Query("SELECT * FROM weather ORDER BY create_time DESC LIMIT 1")
    fun getLatestWeather(): WeatherWithConditions
}
