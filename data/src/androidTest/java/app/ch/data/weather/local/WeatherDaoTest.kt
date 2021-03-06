package app.ch.data.weather.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.test.filters.SmallTest
import app.ch.base.test.data.local.MockWeatherData
import app.ch.base.test.data.local.populateWeatherData
import app.ch.data.base.local.DaoProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
class WeatherDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var daoProvider: DaoProvider

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        daoProvider.close()
    }

    @Test
    fun insert_and_query() {
        expectThat(daoProvider.getWeatherDao().getLatestWeather())
            .isNull()

        daoProvider.getWeatherDao().apply {
            insertWeather(MockWeatherData.weather)
            insertAllConditions(MockWeatherData.conditions)
        }

        expectThat(daoProvider.getWeatherDao().getLatestWeather())
            .isNotNull()
    }

    @Test
    fun insert_duplicated_data_will_not_create_new_record() {
        daoProvider.getWeatherDao().apply {
            insertWeather(MockWeatherData.weather)
            insertAllConditions(MockWeatherData.conditions)

            insertWeather(MockWeatherData.weather)
            insertAllConditions(MockWeatherData.conditions)
        }

        expectThat(daoProvider.query(SimpleSQLiteQuery("SELECT * FROM weather")))
            .get { count }
            .isEqualTo(1)
    }

    @Test
    fun delete_single_item() {
        daoProvider.populateWeatherData()

        daoProvider.getWeatherDao().deleteWeather(1)

        expectThat(daoProvider.query(SimpleSQLiteQuery("SELECT * FROM weather")))
            .get { count }
            .isEqualTo(2)
    }

    @Test
    fun delete_all() {
        daoProvider.populateWeatherData()

        daoProvider.getWeatherDao().deleteAllWeather()

        expectThat(daoProvider.getWeatherDao().getLatestWeather())
            .isNull()
    }
}
