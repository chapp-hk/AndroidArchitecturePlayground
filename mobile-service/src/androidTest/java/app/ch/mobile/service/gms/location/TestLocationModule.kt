package app.ch.mobile.service.gms.location

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.CancellationToken
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [LocationModule::class]
)
class TestLocationModule {

    @Provides
    @Singleton
    internal fun providesFusedLocationProviderClient(): FusedLocationProviderClient {
        return mockk(relaxed = true, relaxUnitFun = true)
    }

    @Provides
    @Singleton
    internal fun providesCancellationToken(): CancellationToken {
        return mockk(relaxed = true, relaxUnitFun = true)
    }

    @Provides
    @Singleton
    internal fun providesLocationRequest(): LocationRequest {
        return mockk(relaxed = true, relaxUnitFun = true)
    }

    @Provides
    @Singleton
    internal fun providesLocationSettingsRequest(): LocationSettingsRequest {
        return mockk(relaxed = true, relaxUnitFun = true)
    }

    @Provides
    @Singleton
    internal fun providesLocationSettingsClient(): SettingsClient {
        return mockk(relaxed = true, relaxUnitFun = true)
    }
}
