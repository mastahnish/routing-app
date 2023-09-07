package pl.jacekrys.routingapp.feature.route.data

import pl.jacekrys.routingapp.core.exception.callOrThrow
import pl.jacekrys.routingapp.core.network.NetworkStateProvider
import pl.jacekrys.routingapp.feature.route.data.local.RouteDao
import pl.jacekrys.routingapp.feature.route.data.local.model.RouteCached
import pl.jacekrys.routingapp.feature.route.data.remote.RouteApi
import pl.jacekrys.routingapp.feature.route.data.remote.RoutingApi
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.model.RouteDetails
import pl.jacekrys.routingapp.feature.route.domain.repository.RouteRepository
import pl.jacekrys.routingapp.feature.route.exception.RoutesErrorWrapper
import pl.jacekrys.routingapp.feature.route.exception.RoutingErrorWrapper

class RouteRepositoryImpl(
    private val routeApi: RouteApi,
    private val routingApi: RoutingApi,
    private val routesErrorWrapper: RoutesErrorWrapper,
    private val routingErrorWrapper: RoutingErrorWrapper,
    private val networkStateProvider: NetworkStateProvider,
    private val routeDao: RouteDao
) : RouteRepository {
    override suspend fun getRoutes(): List<Route> {
        return callOrThrow(routesErrorWrapper) {
            if (networkStateProvider.isNetworkAvailable())
                routeApi.getRoutes().data
                    .map { it.toDomain() }
                    .also {
                        routeDao.saveRoutes(*it.toTypedArray())
                    }
            else getRoutesFromDb()
        }
    }

    private suspend fun getRoutesFromDb(): List<Route> {
        return routeDao.getRoutes().map { it.toDomain() }
    }

    override suspend fun getRoute(id: String): Route {
        return callOrThrow(routesErrorWrapper) {
            if (networkStateProvider.isNetworkAvailable())
                routeApi.getRoute(id)
                    .toDomain()
            else getRouteFromDb(id)
        }
    }

    private suspend fun getRouteFromDb(id: String): Route {
        return routeDao.getRouteById(id).toDomain()
    }

    override suspend fun getRouteDetails(route: Route): RouteDetails {
        return callOrThrow(routingErrorWrapper) {
            routingApi.getOptimalRoute(
                waypoints = mapListOfCoordinatesToQueryParameter(route.stops?.map { it.coordinates }
                    ?: emptyList())
            ).results.first()
                .toDomain(route.id)
        }
    }

//    private suspend fun getRouteDetailsFromDb(id: String): RouteDetails {
//        return routeDao.getRouteDetails(id).toDomain()
//    }
}