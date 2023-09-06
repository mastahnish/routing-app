package pl.jacekrys.routingapp.feature.route.exception

sealed class RoutingError : Throwable() {
    object WrongRouteFormat : RoutingError()
    object CalculationError : RoutingError()
    object RoutesNotFound : RoutingError()
    object Unknown : RoutingError()
}
