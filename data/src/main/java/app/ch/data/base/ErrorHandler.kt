package app.ch.data.base

import app.ch.data.location.remote.LocationUnavailableException
import app.ch.domain.base.ErrorEntity
import app.ch.domain.base.IErrorHandler
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject

class ErrorHandler @Inject
constructor() : IErrorHandler {

    override fun invoke(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException -> ErrorEntity.Network
            is HttpException -> {
                when (throwable.code()) {
                    HTTP_UNAUTHORIZED -> ErrorEntity.AccessDenied
                    HTTP_NOT_FOUND -> ErrorEntity.NotFound
                    429 -> ErrorEntity.LimitExceeded
                    else -> ErrorEntity.Unknown
                }
            }
            is LocationUnavailableException -> ErrorEntity.LocationUnavailable
            else -> ErrorEntity.Unknown
        }
    }
}
