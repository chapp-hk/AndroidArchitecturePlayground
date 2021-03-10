package app.ch.data.weather.remote

import app.ch.base.test.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class WeatherRemoteDataSourceTest {

    @MockK
    private lateinit var weatherApi: WeatherApi

    @MockK
    private lateinit var weatherResponse: WeatherResponse

    private lateinit var weatherRemoteDataSource: WeatherRemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        weatherRemoteDataSource = WeatherRemoteDataSource(weatherApi)
    }

    @Test
    fun getWeatherByCityName() {
        coEvery {
            weatherApi.getWeatherByCityName(any())
        } returns weatherResponse

        weatherRemoteDataSource.getWeatherByCityName("Hong Kong").test()

        coVerify {
            weatherApi.getWeatherByCityName("Hong Kong")
        }
    }

    @Test
    fun getWeatherByLocation() {
        coEvery {
            weatherApi.getWeatherByLocation(any(), any())
        } returns weatherResponse

        weatherRemoteDataSource.getWeatherByLocation(1.2, 3.4).test()

        coVerify {
            weatherApi.getWeatherByLocation(1.2, 3.4)
        }
    }
}
