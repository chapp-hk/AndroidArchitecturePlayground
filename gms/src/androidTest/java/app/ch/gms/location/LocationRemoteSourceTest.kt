package app.ch.gms.location

import android.Manifest
import android.location.Location
import android.location.LocationManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import app.ch.data.location.remote.LocationUnavailableException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.Tasks
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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

    @MockK
    private lateinit var locationSettingsClient: SettingsClient

    @MockK
    private lateinit var locationSettingsRequest: LocationSettingsRequest

    private lateinit var locationRemoteSource: LocationRemoteSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        locationRemoteSource = LocationRemoteSource(
            ApplicationProvider.getApplicationContext(),
            fusedLocationClient,
            cancellationToken,
            locationSettingsClient,
            locationSettingsRequest,
        )
    }

    @Test
    fun verify_fusedLocationClient_getCurrentLocation_invoked() {
        coEvery {
            fusedLocationClient.getCurrentLocation(any(), any())
        } returns Tasks.forResult(Location(LocationManager.GPS_PROVIDER))

        coEvery {
            locationSettingsClient.checkLocationSettings(any())
        } returns Tasks.forResult(LocationSettingsResponse())

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

    @Test(expected = LocationUnavailableException::class)
    fun fusedLocationClient_getCurrentLocation_throws_ResolvableApiException() {
        coEvery {
            fusedLocationClient.getCurrentLocation(any(), any())
        } returns Tasks.forResult(Location(LocationManager.GPS_PROVIDER))

        coEvery {
            locationSettingsClient.checkLocationSettings(any())
        } throws mockk<ResolvableApiException>()

        runBlockingTest {
            locationRemoteSource.getCurrentLocation().collect()
        }
    }

    @Test(expected = Throwable::class)
    fun fusedLocationClient_getCurrentLocation_throws_Throwable() {
        coEvery {
            fusedLocationClient.getCurrentLocation(any(), any())
        } returns Tasks.forResult(Location(LocationManager.GPS_PROVIDER))

        coEvery {
            locationSettingsClient.checkLocationSettings(any())
        } throws Throwable()

        runBlockingTest {
            locationRemoteSource.getCurrentLocation().collect()
        }
    }
}
