package pl.jacekrys.routingapp.feature.route.domain.usecase

import pl.jacekrys.routingapp.core.base.Resource
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.model.RouteDetails
import pl.jacekrys.routingapp.feature.route.domain.repository.RouteRepository

class GetRouteDetailsUseCase(
    private val repository: RouteRepository
) {
    suspend operator fun invoke(route: Route): Resource<RouteDetails> {
        return try {
            Resource.Success(repository.getRouteDetails(route = route))
        } catch (e: Throwable) {
            Resource.Error(e)
        }
    }
}