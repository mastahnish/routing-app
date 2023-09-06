package pl.jacekrys.routingapp.feature.route.presentation

import android.content.Context
import pl.jacekrys.routingapp.R
import pl.jacekrys.routingapp.core.exception.ErrorMapper
import pl.jacekrys.routingapp.feature.route.exception.RoutesServerError

class RoutesErrorMapper(
    private val context: Context
) : ErrorMapper {
    override fun map(error: Throwable): String {
        return when (error) {
            RoutesServerError.RoutesNotFound -> context.getString(R.string.routes_not_found)
            RoutesServerError.ServerError -> context.getString(R.string.server_error)
            RoutesServerError.Unknown -> context.getString(R.string.unknown_error)
            else -> context.getString(R.string.unknown_error)
        }
    }
}