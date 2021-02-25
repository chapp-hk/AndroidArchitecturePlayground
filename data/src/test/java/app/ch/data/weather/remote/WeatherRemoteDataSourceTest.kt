package app.ch.data.weather.remote

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
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

        runBlockingTest {
            weatherRemoteDataSource.getWeatherByCityName("Hong Kong").collect()
        }

        coVerify(exactly = 1) {
            weatherApi.getWeatherByCityName("Hong Kong")
        }
    }
}
