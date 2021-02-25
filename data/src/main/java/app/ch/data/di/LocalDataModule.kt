package app.ch.data.di

import android.content.Context
import androidx.room.Room
import app.ch.data.base.local.DaoProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    private val appDatabaseName = "WeatherApp.db"

    @Provides
    internal fun providesDatabase(@ApplicationContext context: Context): DaoProvider {
        return Room.databaseBuilder(
            context,
            DaoProvider::class.java,
            appDatabaseName
        ).build()
    }
}
