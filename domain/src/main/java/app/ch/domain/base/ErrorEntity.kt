package app.ch.domain.base

sealed class ErrorEntity {

    object Network : ErrorEntity()

    object NotFound : ErrorEntity()

    object AccessDenied : ErrorEntity()

    object LimitExceeded : ErrorEntity()

    object LocationUnavailable : ErrorEntity()

    object Unknown : ErrorEntity()
}
