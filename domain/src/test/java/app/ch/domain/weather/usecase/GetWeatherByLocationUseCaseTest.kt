package app.ch.domain.weather.usecase

import app.ch.domain.weather.repository.IWeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetWeatherByLocationUseCaseTest {

    @MockK
    private lateinit var weatherRepository: IWeatherRepository

    private val ioDispatcher = TestCoroutineDispatcher()

    private lateinit var getWeatherByLocationUseCase: GetWeatherByLocationUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getWeatherByLocationUseCase = GetWeatherByLocationUseCase(
            ioDispatcher,
            weatherRepository
        )
    }

    @Test
    fun invoke() {
        coEvery {
            weatherRepository.getWeatherByLocation(any(), any())
        } returns flowOf()

        runBlockingTest {
            getWeatherByLocationUseCase(1.3, 3.4)
        }

        coVerify {
            weatherRepository.getWeatherByLocation(1.3, 3.4)
        }
    }
}
