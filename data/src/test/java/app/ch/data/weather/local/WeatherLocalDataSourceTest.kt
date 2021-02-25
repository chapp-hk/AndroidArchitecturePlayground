package app.ch.data.weather.local

import app.ch.data.weather.mapper.toDaoEntity
import app.ch.data.weather.model.WeatherModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherLocalDataSourceTest {

    @MockK
    private lateinit var weatherDao: WeatherDao

    @MockK
    private lateinit var weatherModel: WeatherModel

    private lateinit var weatherLocalDataSource: WeatherLocalDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        weatherLocalDataSource = WeatherLocalDataSource(weatherDao)
    }

    @Test
    fun insertWeather() {
        coEvery {
            weatherDao.insertWeather(any())
        } just Runs

        coEvery {
            weatherDao.insertAllConditions(any())
        } just Runs

        runBlockingTest {
            weatherLocalDataSource.insertWeather(weatherModel)
        }

        coVerifySequence {
            weatherDao.insertWeather(weatherModel.toDaoEntity())
            weatherDao.insertAllConditions(weatherModel.conditions.map { it.toDaoEntity() })
        }
    }

    @Test
    fun getWeatherHistory() {
        coEvery {
            weatherDao.getWeathers()
        } returns listOf()

        runBlockingTest {
            weatherLocalDataSource.getWeatherHistory().collect()
        }

        coVerify(exactly = 1) {
            weatherDao.getWeathers()
        }
    }
}
