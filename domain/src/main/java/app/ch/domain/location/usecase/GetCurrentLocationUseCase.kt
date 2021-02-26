package app.ch.domain.location.usecase

import app.ch.domain.di.IoDispatcher
import app.ch.domain.di.MainDispatcher
import app.ch.domain.location.entity.LocationEntity
import app.ch.domain.location.repository.ILocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject
constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val locationRepository: ILocationRepository,
) {

    suspend operator fun invoke(): Flow<LocationEntity> {
        return withContext(mainDispatcher) {
            locationRepository.getCurrentLocation()
                .flowOn(ioDispatcher)
        }
    }
}
