package pl.jacekrys.routingapp.feature.route.domain.repository

import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.model.RouteDetails

interface RouteRepository {
    suspend fun getRoutes(): List<Route>

    suspend fun getRoute(id: String): Route

    suspend fun getRouteDetails(route: Route): RouteDetails
}