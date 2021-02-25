package app.ch.data.weather.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.ch.data.weather.mock.MockData
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
    fun tearDown() {
        daoProvider.close()
    }

    @Test
    fun insert_and_query() {
        val weatherWithConditions = WeatherWithConditions(MockData.weather, MockData.conditions)

        runBlockingTest {
            expectThat(weatherDao.getWeathers())
                .isEmpty()

            weatherDao.insertWeather(MockData.weather)
            weatherDao.insertAllConditions(MockData.conditions)

            expectThat(weatherDao.getWeathers())
                .first()
                .isEqualTo(weatherWithConditions)
        }
    }

    @Test
    fun insert_duplicated_data_will_not_create_new_record() {
        runBlockingTest {
            weatherDao.insertWeather(MockData.weather)
            weatherDao.insertAllConditions(MockData.conditions)

            weatherDao.insertWeather(MockData.weather)
            weatherDao.insertAllConditions(MockData.conditions)

            expectThat(weatherDao.getWeathers())
                .size
                .isEqualTo(1)
        }
    }
}
