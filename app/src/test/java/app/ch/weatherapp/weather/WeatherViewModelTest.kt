package app.ch.weatherapp.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.ch.data.location.remote.LocationUnavailableException
import app.ch.domain.base.IErrorHandler
import app.ch.domain.location.entity.LocationEntity
import app.ch.domain.location.usecase.GetCurrentLocationUseCase
import app.ch.domain.weather.usecase.GetWeatherByCityNameUseCase
import app.ch.domain.weather.usecase.GetWeatherByLocationUseCase
import app.ch.weatherapp.test
import app.ch.weatherapp.weather.mock.MockData
import com.jraska.livedata.test
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getWeatherByCityName: GetWeatherByCityNameUseCase

    @MockK
    private lateinit var getWeatherByLocation: GetWeatherByLocationUseCase

    @MockK
    private lateinit var getCurrentLocation: GetCurrentLocationUseCase

    @MockK
    private lateinit var handleError: IErrorHandler

    private lateinit var weatherViewModel: WeatherViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        weatherViewModel = WeatherViewModel(
            getWeatherByCityName,
            getWeatherByLocation,
            getCurrentLocation,
            handleError
        )
    }

    @Test
    fun `queryWeatherByCityName should invoke getWeatherByCityName`() {
        coEvery {
            getWeatherByCityName(any())
        } returns flowOf(MockData.weatherEntity)

        runBlockingTest {
            weatherViewModel.searchText.value = "Hong Kong"
            weatherViewModel.queryWeatherByCityName()
        }

        coVerify(exactly = 1) {
            getWeatherByCityName("Hong Kong")
        }
    }

    @Test
    fun `queryWeatherByCityName assert start and complete states`() {
        coEvery {
            getWeatherByCityName(any())
        } returns flowOf(MockData.weatherEntity)

        val isLoadingTestObserver = weatherViewModel.isLoading.test()

        runBlockingTest {
            weatherViewModel.queryWeatherByCityName()
        }

        //assert values in LiveData and SharedFlow
        weatherViewModel.startSearchEvent.test {
            expectThat(it.size).isEqualTo(1)
        }

        isLoadingTestObserver.assertValueHistory(
            false, true, false
        )
    }

    @Test
    fun `queryWeatherByCityName assert error states`() {
        coEvery {
            getWeatherByCityName(any())
        } returns flow { throw Throwable() }

        runBlockingTest {
            weatherViewModel.queryWeatherByCityName()
        }

        //assert values in LiveData and SharedFlow
        weatherViewModel.errorEvent.test {
            expectThat(it).isNotEmpty()
        }
    }

    @Test
    fun `queryWeatherByCityName assert success states`() {
        coEvery {
            getWeatherByCityName(any())
        } returns flowOf(MockData.weatherEntity)

        runBlockingTest {
            weatherViewModel.queryWeatherByCityName()
        }

        //assert values in LiveData
        weatherViewModel.cityName.test()
            .assertValue(MockData.weatherEntity.name)

        weatherViewModel.temperature.test()
            .assertValue(MockData.weatherEntity.temperature.toString())

        weatherViewModel.feelsLike.test()
            .assertValue(MockData.weatherEntity.feelsLike.toString())

        weatherViewModel.temperatureMin.test()
            .assertValue(MockData.weatherEntity.temperatureMin.toString())

        weatherViewModel.temperatureMax.test()
            .assertValue(MockData.weatherEntity.temperatureMax.toString())

        weatherViewModel.pressure.test()
            .assertValue(MockData.weatherEntity.pressure.toString())

        weatherViewModel.humidity.test()
            .assertValue(MockData.weatherEntity.humidity.toString())

        weatherViewModel.visibility.test()
            .assertValue(MockData.weatherEntity.visibility.toString())

        weatherViewModel.windSpeed.test()
            .assertValue(MockData.weatherEntity.windSpeed.toString())

        weatherViewModel.windDeg.test()
            .assertValue(MockData.weatherEntity.windDeg.toString())

        weatherViewModel.cloudiness.test()
            .assertValue(MockData.weatherEntity.cloudiness.toString())
    }

    @Test
    fun `queryCurrentLocation should invoke getCurrentLocation and getWeatherByLocation`() {
        coEvery {
            getCurrentLocation()
        } returns flowOf(LocationEntity(12.2, 21.2))

        coEvery {
            getWeatherByLocation(any(), any())
        } returns flowOf(MockData.weatherEntity)

        runBlockingTest {
            weatherViewModel.queryCurrentLocation()
        }

        coVerifySequence {
            getCurrentLocation()
            getWeatherByLocation(12.2, 21.2)
        }
    }

    @Test
    fun `queryCurrentLocation assert error states`() {
        coEvery {
            getCurrentLocation()
        } returns flow { throw mockk<LocationUnavailableException>() }

        runBlockingTest {
            weatherViewModel.queryCurrentLocation()
        }

        //assert values in LiveData and SharedFlow
        weatherViewModel.errorEvent.test {
            expectThat(it).isNotEmpty()
        }

        coVerify(exactly = 0) {
            getWeatherByLocation(any(), any())
        }
    }
}
