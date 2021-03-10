package app.ch.mobile.service.gms.location

import android.Manifest
import android.location.Location
import android.location.LocationManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import app.ch.base.test.test
import app.ch.data.location.remote.LocationUnavailableException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.Tasks
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
        every {
            fusedLocationClient.getCurrentLocation(any(), any())
        } returns Tasks.forResult(Location(LocationManager.GPS_PROVIDER))

        every {
            locationSettingsClient.checkLocationSettings(any())
        } returns Tasks.forResult(LocationSettingsResponse())

        locationRemoteSource.getCurrentLocation().test()

        verify {
            fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationToken
            )
        }
    }

    @Test(expected = LocationUnavailableException::class)
    fun fusedLocationClient_getCurrentLocation_throws_ResolvableApiException() {
        every {
            fusedLocationClient.getCurrentLocation(any(), any())
        } returns Tasks.forResult(Location(LocationManager.GPS_PROVIDER))

        every {
            locationSettingsClient.checkLocationSettings(any())
        } throws mockk<ResolvableApiException>()

        locationRemoteSource.getCurrentLocation().test()
    }

    @Test(expected = Throwable::class)
    fun fusedLocationClient_getCurrentLocation_throws_Throwable() {
        every {
            fusedLocationClient.getCurrentLocation(any(), any())
        } returns Tasks.forResult(Location(LocationManager.GPS_PROVIDER))

        every {
            locationSettingsClient.checkLocationSettings(any())
        } throws Throwable()

        locationRemoteSource.getCurrentLocation().test()
    }
}
