package app.ch.base.test.data.local

import android.content.Context
import androidx.room.Room
import app.ch.data.base.local.DaoProvider
import app.ch.data.di.LocalDataModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [LocalDataModule::class]
)
class TestLocalDataModule {

    @Provides
    @Singleton
    internal fun providesDatabase(@ApplicationContext context: Context): DaoProvider {
        return Room.inMemoryDatabaseBuilder(context, DaoProvider::class.java)
            .allowMainThreadQueries()
            .build()
    }
}

