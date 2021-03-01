package app.ch.domain.weather.usecase

import app.ch.domain.weather.repository.IWeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetLatestSearchedWeatherUseCaseTest {

    @MockK
    private lateinit var weatherRepository: IWeatherRepository

    private val ioDispatcher = TestCoroutineDispatcher()

    private lateinit var getLatestSearchedWeatherUseCase: GetLatestSearchedWeatherUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getLatestSearchedWeatherUseCase = GetLatestSearchedWeatherUseCase(
            ioDispatcher,
            weatherRepository
        )
    }

    @Test
    fun invoke() {
        every {
            weatherRepository.getLatestSearchedWeather()
        } returns flowOf()

        getLatestSearchedWeatherUseCase()

        verify(exactly = 1) {
            weatherRepository.getLatestSearchedWeather()
        }
    }
}
