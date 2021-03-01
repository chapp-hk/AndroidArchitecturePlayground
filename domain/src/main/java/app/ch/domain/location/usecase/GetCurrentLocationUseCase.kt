package app.ch.domain.location.usecase

import app.ch.domain.di.IoDispatcher
import app.ch.domain.location.entity.LocationEntity
import app.ch.domain.location.repository.ILocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject
constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val locationRepository: ILocationRepository,
) {

    operator fun invoke(): Flow<LocationEntity> {
        return locationRepository.getCurrentLocation()
            .flowOn(ioDispatcher)
    }
}
