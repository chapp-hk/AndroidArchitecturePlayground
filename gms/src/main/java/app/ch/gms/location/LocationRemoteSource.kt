package app.ch.gms.location

import android.Manifest
import android.content.Context
import androidx.core.content.ContextCompat
import app.ch.data.location.model.LocationModel
import app.ch.data.location.remote.ILocationRemoteDataSource
import app.ch.data.location.remote.LocationUnavailableException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.CancellationToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationRemoteSource @Inject
constructor(
    @ApplicationContext private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val cancellationToken: CancellationToken,
    private val locationSettingsClient: SettingsClient,
    private val locationSettingsRequest: LocationSettingsRequest,
) : ILocationRemoteDataSource {

    override suspend fun getCurrentLocation(): Flow<LocationModel> {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        return flow {
            requireLocation()
            fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationToken
            ).await().let {
                emit(it.toDataModel())
            }
        }
    }

    private suspend fun requireLocation() {
        runCatching {
            locationSettingsClient.checkLocationSettings(locationSettingsRequest).await()
        }.getOrElse {
            throw when (it) {
                is ResolvableApiException -> LocationUnavailableException()
                else -> it
            }
        }
    }
}
