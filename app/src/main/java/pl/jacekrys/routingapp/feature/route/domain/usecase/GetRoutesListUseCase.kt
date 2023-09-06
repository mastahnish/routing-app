package pl.jacekrys.routingapp.feature.route.domain.usecase

import pl.jacekrys.routingapp.core.base.Resource
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.repository.RouteRepository

class GetRoutesListUseCase(
    private val repository: RouteRepository
) {
    suspend operator fun invoke(): Resource<List<Route>> {
        return try {
            Resource.Success(repository.getRoutes())
        } catch (e: Throwable) {
            Resource.Error(e)
        }
    }
}