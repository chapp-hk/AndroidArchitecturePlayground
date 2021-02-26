package app.ch.domain.location.usecase

import app.ch.domain.location.repository.ILocationRepository
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
class GetCurrentLocationUseCaseTest {

    @MockK
    private lateinit var locationRepository: ILocationRepository

    private val ioDispatcher = TestCoroutineDispatcher()

    private val mainDispatcher = TestCoroutineDispatcher()

    private lateinit var getCurrentLocationUseCase: GetCurrentLocationUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getCurrentLocationUseCase = GetCurrentLocationUseCase(
            ioDispatcher,
            mainDispatcher,
            locationRepository
        )
    }

    @Test
    fun invoke() {
        coEvery {
            locationRepository.getCurrentLocation()
        } returns flowOf()

        runBlockingTest {
            getCurrentLocationUseCase()
        }

        coVerify(exactly = 1) {
            locationRepository.getCurrentLocation()
        }
    }
}
