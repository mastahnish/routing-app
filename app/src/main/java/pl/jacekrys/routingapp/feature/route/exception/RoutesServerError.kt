package pl.jacekrys.routingapp.feature.route.exception

sealed class RoutesServerError : Throwable() {
    object RoutesNotFound : RoutesServerError()
    object ServerError : RoutesServerError()
    object Unknown : RoutesServerError()
}
