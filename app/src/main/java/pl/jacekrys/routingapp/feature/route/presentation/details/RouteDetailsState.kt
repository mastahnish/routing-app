package pl.jacekrys.routingapp.feature.route.presentation.details

import pl.jacekrys.routingapp.feature.route.presentation.model.RouteDetailsDisplayable
import pl.jacekrys.routingapp.feature.route.presentation.model.RouteDisplayable

data class RouteDetailsState(
    val route: RouteDisplayable? = null,
    val isLoading: Boolean = false,
    val routeDetails: RouteDetailsDisplayable? = null,
    val routeDetailsError: Boolean = false,
    val routeError: Boolean = false,
)
