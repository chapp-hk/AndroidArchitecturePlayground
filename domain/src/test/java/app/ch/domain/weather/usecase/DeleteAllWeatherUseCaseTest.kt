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
class DeleteAllWeatherUseCaseTest {

    @MockK
    private lateinit var weatherRepository: IWeatherRepository

    private val ioDispatcher = TestCoroutineDispatcher()

    private lateinit var deleteAllWeatherUseCase: DeleteAllWeatherUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        deleteAllWeatherUseCase = DeleteAllWeatherUseCase(
            ioDispatcher,
            weatherRepository
        )
    }

    @Test
    fun invoke() {
        every {
            weatherRepository.deleteAllWeather()
        } returns flowOf()

        deleteAllWeatherUseCase()

        verify {
            weatherRepository.deleteAllWeather()
        }
    }
}
