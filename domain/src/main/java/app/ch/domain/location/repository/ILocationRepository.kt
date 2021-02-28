package app.ch.domain.location.repository

import app.ch.domain.location.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

interface ILocationRepository {

    fun getCurrentLocation(): Flow<LocationEntity>
}
