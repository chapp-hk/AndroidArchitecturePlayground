package app.ch.domain.weather.usecase

import app.ch.domain.base.CoroutineDispatcherProvider
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

    @MockK
    private lateinit var coroutineDispatcherProvider: CoroutineDispatcherProvider

    private lateinit var getLatestSearchedWeatherUseCase: GetLatestSearchedWeatherUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every {
            coroutineDispatcherProvider.ioDispatcher
        } returns TestCoroutineDispatcher()

        getLatestSearchedWeatherUseCase = GetLatestSearchedWeatherUseCase(
            coroutineDispatcherProvider,
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
