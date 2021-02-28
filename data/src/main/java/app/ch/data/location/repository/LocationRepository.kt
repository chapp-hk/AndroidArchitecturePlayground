package app.ch.data.location.repository

import app.ch.data.location.mapper.toDomainEntity
import app.ch.data.location.remote.ILocationRemoteDataSource
import app.ch.domain.location.entity.LocationEntity
import app.ch.domain.location.repository.ILocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepository @Inject
constructor(
    private val remoteDataSource: ILocationRemoteDataSource
) : ILocationRepository {

    override fun getCurrentLocation(): Flow<LocationEntity> {
        return remoteDataSource.getCurrentLocation().map {
            it.toDomainEntity()
        }
    }
}
