package app.ch.gms.location

import android.content.Context
import app.ch.data.location.remote.ILocationRemoteDataSource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocationModule {

    @Provides
    internal fun providesFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context.applicationContext)
    }

    @Provides
    internal fun providesCancellationToken(): CancellationToken {
        return CancellationTokenSource().token
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationSourceModule {

    @Binds
    internal abstract fun bindsLocationSource(locationSource: LocationRemoteSource): ILocationRemoteDataSource
}
