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
class GetWeatherByCityNameUseCaseTest {

    @MockK
    private lateinit var weatherRepository: IWeatherRepository

    private val ioDispatcher = TestCoroutineDispatcher()

    private val mainDispatcher = TestCoroutineDispatcher()

    private lateinit var getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getWeatherByCityNameUseCase = GetWeatherByCityNameUseCase(
            ioDispatcher,
            mainDispatcher,
            weatherRepository
        )
    }

    @Test
    fun invoke() {
        coEvery {
            weatherRepository.getWeatherByCityName(any())
        } returns flowOf()

        runBlockingTest {
            getWeatherByCityNameUseCase("Hong Kong")
        }

        coVerify(exactly = 1) {
            weatherRepository.getWeatherByCityName("Hong Kong")
        }
    }
}
