package app.ch.data.weather.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.ch.data.base.local.DaoProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.first
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.size

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var daoProvider: DaoProvider

    private lateinit var weatherDao: WeatherDao

    @Before
    fun setUp() {
        daoProvider = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DaoProvider::class.java
        ).build()

        weatherDao = daoProvider.getWeatherDao()
    }

    @After
    fun closeDb() {
        daoProvider.close()
    }

    @Test
    fun insert_and_query() {
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

        val weatherWithConditions = WeatherWithConditions(weather, conditions)

        runBlockingTest {
            expectThat(weatherDao.getWeathers())
                .isEmpty()

            weatherDao.insertWeather(weather)
            weatherDao.insertAllConditions(conditions)

            expectThat(weatherDao.getWeathers())
                .first()
                .isEqualTo(weatherWithConditions)
        }
    }

    @Test
    fun insert_duplicated_data_will_not_create_new_record() {
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

        runBlockingTest {
            weatherDao.insertWeather(weather)
            weatherDao.insertAllConditions(conditions)

            weatherDao.insertWeather(weather)
            weatherDao.insertAllConditions(conditions)

            expectThat(weatherDao.getWeathers())
                .size
                .isEqualTo(1)
        }
    }
}
