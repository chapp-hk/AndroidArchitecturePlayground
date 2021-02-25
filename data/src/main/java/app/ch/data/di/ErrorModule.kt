package app.ch.data.di

import app.ch.data.base.ErrorHandler
import app.ch.domain.base.IErrorHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {

    @Binds
    internal abstract fun bindsErrorHandler(errorHandler: ErrorHandler): IErrorHandler
}
