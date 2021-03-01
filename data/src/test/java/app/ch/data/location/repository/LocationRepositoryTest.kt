package app.ch.data.location.repository

import app.ch.data.location.model.LocationModel
import app.ch.data.location.remote.ILocationRemoteDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LocationRepositoryTest {

    @MockK
    private lateinit var remoteDataSource: ILocationRemoteDataSource

    private lateinit var locationRepository: LocationRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        locationRepository = LocationRepository(remoteDataSource)
    }

    @Test
    fun getCurrentLocation() {
        coEvery {
            remoteDataSource.getCurrentLocation()
        } returns flowOf(LocationModel(13.32, 129.12))

        locationRepository.getCurrentLocation()

        coVerify(exactly = 1) {
            remoteDataSource.getCurrentLocation()
        }
    }
}
