package pl.jacekrys.routingapp.feature.route.data

import pl.jacekrys.routingapp.core.exception.callOrThrow
import pl.jacekrys.routingapp.feature.route.data.remote.RouteApi
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.repository.RouteRepository
import pl.jacekrys.routingapp.feature.route.exception.RoutesErrorWrapper

class RouteRepositoryImpl(
    private val routeApi: RouteApi,
    private val routesErrorWrapper: RoutesErrorWrapper
) : RouteRepository {
    override suspend fun getRoutes(): List<Route> {
        return callOrThrow(routesErrorWrapper) {
            routeApi.getRoutes().data
                .map { it.toDomain() }
        }
    }

    override suspend fun getRoute(id: String): Route {
        TODO("Not yet implemented")
    }
}