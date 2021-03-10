package app.ch.data.weather.local

import app.ch.base.test.test
import app.ch.data.weather.model.WeatherModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

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
        every {
            weatherDao.insertWeather(any())
        } just Runs

        every {
            weatherDao.insertAllConditions(any())
        } just Runs

        weatherLocalDataSource.insertWeather(weatherModel)

        verifySequence {
            weatherDao.insertWeather(any())
            weatherDao.insertAllConditions(any())
        }
    }

    @Test
    fun getWeatherHistory() {
        every {
            weatherDao.getWeathers()
        } returns mockk(relaxed = true)

        weatherLocalDataSource.getWeatherHistory().test()

        verify(exactly = 1) {
            weatherDao.getWeathers()
        }
    }

    @Test
    fun `getLatestWeather success`() {
        every {
            weatherDao.getLatestWeather()
        } returns mockk(relaxed = true)

        weatherLocalDataSource.getLatestWeather().test()

        verify {
            weatherDao.getLatestWeather()
        }
    }

    @Test(expected = EmptyHistoryException::class)
    fun `getLatestWeather empty`() {
        every {
            weatherDao.getLatestWeather()
        } returns null

        weatherLocalDataSource.getLatestWeather().test()
    }

    @Test
    fun deleteWeather() {
        every {
            weatherDao.deleteWeather(any())
        } returns 0

        weatherLocalDataSource.deleteWeather(0).test()

        verify {
            weatherDao.deleteWeather(0)
        }
    }

    @Test
    fun deleteAllWeather() {
        every {
            weatherDao.deleteAllWeather()
        } just Runs

        weatherLocalDataSource.deleteAllWeather().test()

        verify {
            weatherDao.deleteAllWeather()
        }
    }
}
