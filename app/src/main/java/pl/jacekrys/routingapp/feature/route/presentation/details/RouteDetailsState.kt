package pl.jacekrys.routingapp.feature.route.presentation.details

import pl.jacekrys.routingapp.feature.route.presentation.model.RouteDisplayable

data class RouteDetailsState(
    val route: RouteDisplayable? = null,
    val isLoading: Boolean = false,
)
