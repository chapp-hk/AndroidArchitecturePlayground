package app.ch.data.location

import app.ch.data.location.repository.LocationRepository
import app.ch.domain.location.repository.ILocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationRepositoryModule {

    @Binds
    internal abstract fun bindsLocationRepository(repository: LocationRepository): ILocationRepository
}
