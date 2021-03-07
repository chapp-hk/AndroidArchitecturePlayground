package app.ch.base.test.data.remote

import app.ch.data.base.remote.ApiConfig
import app.ch.data.di.ApiConfigModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApiConfigModule::class]
)
class TestApiConfigModule {

    @Provides
    fun providesTestApiConfig(): ApiConfig {
        return ApiConfig(
            baseUrl = "http://localhost:8080",
            apiKey = "test-api-key"
        )
    }
}
