package app.ch.domain.location.usecase

import app.ch.domain.base.CoroutineDispatcherProvider
import app.ch.domain.location.repository.ILocationRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class GetCurrentLocationUseCaseTest {

    @MockK
    private lateinit var locationRepository: ILocationRepository

    @MockK
    private lateinit var coroutineDispatcherProvider: CoroutineDispatcherProvider

    private lateinit var getCurrentLocationUseCase: GetCurrentLocationUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every {
            coroutineDispatcherProvider.ioDispatcher
        } returns TestCoroutineDispatcher()

        getCurrentLocationUseCase = GetCurrentLocationUseCase(
            coroutineDispatcherProvider,
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
