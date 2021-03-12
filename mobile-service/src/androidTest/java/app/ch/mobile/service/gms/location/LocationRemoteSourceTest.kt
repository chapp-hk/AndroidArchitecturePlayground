package app.ch.mobile.service.gms.location

import android.Manifest
import androidx.test.filters.SmallTest
import androidx.test.rule.GrantPermissionRule
import app.ch.base.test.test
import app.ch.data.location.remote.LocationUnavailableException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.Tasks
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
class LocationRemoteSourceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION)

    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject
    lateinit var cancellationToken: CancellationToken

    @Inject
    lateinit var locationSettingsClient: SettingsClient

    @Inject
    lateinit var locationRemoteSource: LocationRemoteSource

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun verify_fusedLocationClient_getCurrentLocation_invoked() {
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
            locationSettingsClient.checkLocationSettings(any())
        } throws mockk<ResolvableApiException>()

        locationRemoteSource.getCurrentLocation().test()
    }

    @Test(expected = Throwable::class)
    fun fusedLocationClient_getCurrentLocation_throws_Throwable() {
        every {
            locationSettingsClient.checkLocationSettings(any())
        } throws Throwable()

        locationRemoteSource.getCurrentLocation().test()
    }
}
