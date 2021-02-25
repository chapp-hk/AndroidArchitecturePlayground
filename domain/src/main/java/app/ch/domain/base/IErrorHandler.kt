package app.ch.domain.base

interface IErrorHandler {

    operator fun invoke(throwable: Throwable): ErrorEntity
}
