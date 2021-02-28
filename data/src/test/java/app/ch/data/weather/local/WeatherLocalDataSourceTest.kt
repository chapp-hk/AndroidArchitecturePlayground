package app.ch.data.weather.local

import app.ch.data.weather.model.WeatherModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
            weatherDao.insertWeather(any())
            weatherDao.insertAllConditions(any())
        }
    }

    @Test
    fun getWeatherHistory() {
        coEvery {
            weatherDao.getWeathers()
        } returns mockk(relaxed = true)

        runBlockingTest {
            val job = launch {
                weatherLocalDataSource.getWeatherHistory().collect()
            }
            job.cancel()
        }

        coVerify(exactly = 1) {
            weatherDao.getWeathers()
        }
    }
}
