package pl.jacekrys.routingapp.feature.route.domain.repository

import pl.jacekrys.routingapp.feature.route.domain.model.Route

interface RouteRepository {
    fun getRoutes(): List<Route>

    fun getRoute(id: String): Route
}