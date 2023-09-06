package pl.jacekrys.routingapp.feature.route.exception

import pl.jacekrys.routingapp.core.exception.ErrorWrapper
import retrofit2.HttpException

class RoutesErrorWrapper : ErrorWrapper {
    override fun wrap(throwable: Throwable): Throwable {
        return when (throwable) {
            is HttpException -> wrapHttpError(throwable)
            else -> throwable
        }
    }

    private fun wrapHttpError(httpException: HttpException): Throwable {
        return with(httpException) {
            when (code()) {
                404 -> RoutesServerError.RoutesNotFound
                500 -> RoutesServerError.ServerError
                else -> RoutesServerError.Unknown
            }
        }
    }
}