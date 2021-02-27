package app.ch.data.base

import app.ch.data.location.remote.LocationUnavailableException
import app.ch.domain.base.ErrorEntity
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import retrofit2.HttpException
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.io.IOException
import java.net.HttpURLConnection

class ErrorHandlerTest {

    private val errorHandler = ErrorHandler()

    @Test
    fun `throwable type is IOException`() {
        expectThat(errorHandler(IOException()))
            .isEqualTo(ErrorEntity.Network)
    }

    @Test
    fun `throwable type is HttpException with HTTP_UNAUTHORIZED error code`() {
        val httpException = mockk<HttpException>()
        every { httpException.code() } returns HttpURLConnection.HTTP_UNAUTHORIZED

        expectThat(errorHandler(httpException))
            .isEqualTo(ErrorEntity.AccessDenied)
    }

    @Test
    fun `throwable type is HttpException with HTTP_NOT_FOUND error code`() {
        val httpException = mockk<HttpException>()
        every { httpException.code() } returns HttpURLConnection.HTTP_NOT_FOUND

        expectThat(errorHandler(httpException))
            .isEqualTo(ErrorEntity.NotFound)
    }

    @Test
    fun `throwable type is HttpException with 429 error code`() {
        val httpException = mockk<HttpException>()
        every { httpException.code() } returns 429

        expectThat(errorHandler(httpException))
            .isEqualTo(ErrorEntity.LimitExceeded)
    }

    @Test
    fun `throwable type is HttpException with unhandled error code`() {
        val httpException = mockk<HttpException>()
        every { httpException.code() } returns 721831

        expectThat(errorHandler(httpException))
            .isEqualTo(ErrorEntity.Unknown)
    }

    @Test
    fun `throwable type is LocationUnavailableException`() {
        expectThat(errorHandler(LocationUnavailableException()))
            .isEqualTo(ErrorEntity.LocationUnavailable)
    }

    @Test
    fun `throwable type is unhandled`() {
        expectThat(errorHandler(Throwable()))
            .isEqualTo(ErrorEntity.Unknown)
    }
}
