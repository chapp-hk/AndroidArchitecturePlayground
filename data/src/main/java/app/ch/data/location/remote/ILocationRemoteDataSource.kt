package app.ch.data.location.remote

import app.ch.data.location.model.LocationModel
import kotlinx.coroutines.flow.Flow

interface ILocationRemoteDataSource {

    fun getCurrentLocation(): Flow<LocationModel>
}
