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
class DeleteWeatherUseCaseTest {

    @MockK
    private lateinit var weatherRepository: IWeatherRepository

    private val ioDispatcher = TestCoroutineDispatcher()

    private lateinit var deleteWeatherUseCase: DeleteWeatherUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        deleteWeatherUseCase = DeleteWeatherUseCase(
            ioDispatcher,
            weatherRepository
        )
    }

    @Test
    fun invoke() {
        every {
            weatherRepository.deleteWeather(any())
        } returns flowOf()

        deleteWeatherUseCase(2)

        verify {
            weatherRepository.deleteWeather(2)
        }
    }
}
