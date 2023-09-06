package pl.jacekrys.routingapp.feature.route.domain.repository

import pl.jacekrys.routingapp.feature.route.domain.model.Route

interface RouteRepository {
    suspend fun getRoutes(): List<Route>

    suspend fun getRoute(id: String): Route
}