package app.ch.data.location.remote

import app.ch.data.location.model.LocationModel
import kotlinx.coroutines.flow.Flow

interface ILocationRemoteDataSource {

    suspend fun getCurrentLocation(): Flow<LocationModel>
}
