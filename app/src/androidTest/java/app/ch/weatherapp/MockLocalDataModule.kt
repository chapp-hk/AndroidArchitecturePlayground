package app.ch.weatherapp

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
class MockLocalDataModule {

    @Provides
    internal fun providesDatabase(@ApplicationContext context: Context): DaoProvider {
        return Room.inMemoryDatabaseBuilder(context, DaoProvider::class.java).build()
    }
}
