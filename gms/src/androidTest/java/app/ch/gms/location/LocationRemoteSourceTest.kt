package app.ch.gms.location

import android.Manifest
import android.location.Location
import android.location.LocationManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.Tasks
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LocationRemoteSourceTest {

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION)

    @MockK
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @MockK
    private lateinit var cancellationToken: CancellationToken

    private lateinit var locationRemoteSource: LocationRemoteSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        locationRemoteSource = LocationRemoteSource(
            ApplicationProvider.getApplicationContext(),
            fusedLocationClient,
            cancellationToken
        )
    }

    @Test
    fun verify_fusedLocationClient_getCurrentLocation_invoked() {
        coEvery {
            fusedLocationClient.getCurrentLocation(any(), any())
        } returns Tasks.forResult(Location(LocationManager.GPS_PROVIDER))

        runBlockingTest {
            locationRemoteSource.getCurrentLocation().collect()
        }

        coVerify(exactly = 1) {
            fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationToken
            )
        }
    }
}
