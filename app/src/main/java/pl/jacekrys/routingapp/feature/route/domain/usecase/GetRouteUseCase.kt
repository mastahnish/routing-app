package pl.jacekrys.routingapp.feature.route.domain.usecase

import pl.jacekrys.routingapp.core.base.Resource
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.repository.RouteRepository

class GetRouteUseCase(
    private val routeRepository: RouteRepository
) {
    suspend operator fun invoke(id: String): Resource<Route> {
        return try {
            Resource.Success(routeRepository.getRoute(id))
        } catch (e: Throwable) {
            Resource.Error(e)
        }
    }
}