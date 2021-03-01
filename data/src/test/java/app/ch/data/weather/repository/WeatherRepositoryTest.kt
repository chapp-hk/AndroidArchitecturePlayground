package app.ch.data.weather.repository

import androidx.paging.PagingData
import app.ch.base.test.test
import app.ch.data.weather.local.WeatherLocalDataSource
import app.ch.data.weather.model.WeatherModel
import app.ch.data.weather.remote.WeatherRemoteDataSource
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherRepositoryTest {

    @MockK
    private lateinit var remoteDataSource: WeatherRemoteDataSource

    @MockK
    private lateinit var localDataSource: WeatherLocalDataSource

    @MockK
    private lateinit var weatherModel: WeatherModel

    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        weatherRepository = WeatherRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun getWeatherByCityName() {
        every {
            remoteDataSource.getWeatherByCityName(any())
        } returns flowOf(weatherModel)

        every {
            localDataSource.insertWeather(any())
        } just Runs

        weatherRepository.getWeatherByCityName("Hong Kong").test()

        verify {
            remoteDataSource.getWeatherByCityName("Hong Kong")
            localDataSource.insertWeather(weatherModel)
        }
    }

    @Test
    fun getWeatherByLocation() {
        every {
            remoteDataSource.getWeatherByLocation(any(), any())
        } returns flowOf(weatherModel)

        every {
            localDataSource.insertWeather(any())
        } just Runs

        weatherRepository.getWeatherByLocation(2.3, 4.5).test()

        verify {
            remoteDataSource.getWeatherByLocation(2.3, 4.5)
            localDataSource.insertWeather(weatherModel)
        }
    }

    @Test
    fun getWeatherHistory() {
        every {
            localDataSource.getWeatherHistory()
        } returns flowOf(PagingData.empty())

        weatherRepository.getWeatherHistory()

        verify {
            localDataSource.getWeatherHistory()
        }
    }

    @Test
    fun getLatestSearchedWeather() {
        every {
            localDataSource.getLatestWeather()
        } returns flowOf(weatherModel)

        weatherRepository.getLatestSearchedWeather()

        verify {
            localDataSource.getLatestWeather()
        }
    }
}
