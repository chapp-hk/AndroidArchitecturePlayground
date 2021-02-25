package app.ch.data.weather

import app.ch.data.base.local.DaoProvider
import app.ch.data.weather.local.WeatherDao
import app.ch.data.weather.remote.WeatherApi
import app.ch.data.weather.repository.WeatherRepository
import app.ch.domain.weather.repository.IWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherRepositoryModule {

    @Binds
    internal abstract fun bindsWeatherRepository(repository: WeatherRepository): IWeatherRepository
}

@Module
@InstallIn(SingletonComponent::class)
class WeatherDataSourceModule {

    @Provides
    internal fun providesWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    internal fun providesWeatherDao(daoProvider: DaoProvider): WeatherDao {
        return daoProvider.getWeatherDao()
    }
}
