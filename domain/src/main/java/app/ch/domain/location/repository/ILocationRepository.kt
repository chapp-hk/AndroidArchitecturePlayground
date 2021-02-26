package app.ch.domain.location.repository

import app.ch.domain.location.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

interface ILocationRepository {

    suspend fun getCurrentLocation(): Flow<LocationEntity>
}
