package pl.jacekrys.routingapp.feature.route.exception

import pl.jacekrys.routingapp.core.exception.ErrorWrapper
import retrofit2.HttpException

class RoutingErrorWrapper : ErrorWrapper {
    override fun wrap(throwable: Throwable): Throwable {
        return when (throwable) {
            is HttpException -> wrapHttpError(throwable)
            is NoSuchElementException -> RoutingError.RoutesNotFound
            else -> throwable
        }
    }

    private fun wrapHttpError(httpException: HttpException): Throwable {
        return with(httpException) {
            when (code()) {
                404 -> RoutingError.WrongRouteFormat
                500 -> RoutingError.CalculationError
                else -> RoutingError.Unknown
            }
        }
    }
}