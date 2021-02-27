package app.ch.data.weather.repository

import app.ch.data.weather.local.WeatherLocalDataSource
import app.ch.data.weather.model.WeatherModel
import app.ch.data.weather.remote.WeatherRemoteDataSource
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
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
        coEvery {
            remoteDataSource.getWeatherByCityName(any())
        } returns flowOf(weatherModel)

        coEvery {
            localDataSource.insertWeather(any())
        } just Runs

        runBlockingTest {
            weatherRepository.getWeatherByCityName("Hong Kong").collect()
        }

        coVerifySequence {
            remoteDataSource.getWeatherByCityName("Hong Kong")
            localDataSource.insertWeather(weatherModel)
        }
    }

    @Test
    fun getWeatherByLocation() {
        coEvery {
            remoteDataSource.getWeatherByLocation(any(), any())
        } returns flowOf(weatherModel)

        coEvery {
            localDataSource.insertWeather(any())
        } just Runs

        runBlockingTest {
            weatherRepository.getWeatherByLocation(2.3, 4.5).collect()
        }

        coVerifySequence {
            remoteDataSource.getWeatherByLocation(2.3, 4.5)
            localDataSource.insertWeather(weatherModel)
        }
    }

    @Test
    fun getWeatherHistory() {
        coEvery {
            localDataSource.getWeatherHistory()
        } returns flowOf(listOf())

        runBlockingTest {
            weatherRepository.getWeatherHistory().collect()
        }

        coVerify {
            localDataSource.getWeatherHistory()
        }
    }
}
