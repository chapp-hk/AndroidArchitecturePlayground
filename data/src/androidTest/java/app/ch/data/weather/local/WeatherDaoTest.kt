package app.ch.data.weather.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.ch.data.base.local.DaoProvider
import app.ch.data.weather.mock.MockData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo

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
        val query = SimpleSQLiteQuery("SELECT * FROM weather")

        runBlockingTest {
            expectThat(daoProvider.query(query))
                .get { count }
                .isEqualTo(0)

            weatherDao.insertWeather(MockData.weather)
            weatherDao.insertAllConditions(MockData.conditions)

            expectThat(daoProvider.query(query))
                .get { count }
                .isEqualTo(1)
        }
    }

    @Test
    fun insert_duplicated_data_will_not_create_new_record() {
        val query = SimpleSQLiteQuery("SELECT * FROM weather")

        runBlockingTest {
            weatherDao.insertWeather(MockData.weather)
            weatherDao.insertAllConditions(MockData.conditions)

            weatherDao.insertWeather(MockData.weather)
            weatherDao.insertAllConditions(MockData.conditions)

            expectThat(daoProvider.query(query))
                .get { count }
                .isEqualTo(1)
        }
    }
}
