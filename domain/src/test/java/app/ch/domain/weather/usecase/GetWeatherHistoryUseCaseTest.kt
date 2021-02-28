package app.ch.domain.weather.usecase

import app.ch.domain.weather.repository.IWeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetWeatherHistoryUseCaseTest {

    @MockK
    private lateinit var weatherRepository: IWeatherRepository

    private lateinit var getWeatherHistoryUseCase: GetWeatherHistoryUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getWeatherHistoryUseCase = GetWeatherHistoryUseCase(weatherRepository)
    }

    @Test
    fun invoke() {
        coEvery {
            weatherRepository.getWeatherHistory()
        } returns flowOf()

        runBlockingTest {
            getWeatherHistoryUseCase()
        }

        coVerify(exactly = 1) {
            weatherRepository.getWeatherHistory()
        }
    }
}
