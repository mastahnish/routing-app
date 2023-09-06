package pl.jacekrys.routingapp.feature.route.data

import pl.jacekrys.routingapp.feature.route.data.remote.RouteApi
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.repository.RouteRepository

class RouteRepositoryImpl(
    private val routeApi: RouteApi
) : RouteRepository {
    override suspend fun getRoutes(): List<Route> {
        return routeApi.getRoutes().data
            .map { it.toDomain() }
    }

    override suspend fun getRoute(id: String): Route {
        TODO("Not yet implemented")
    }
}