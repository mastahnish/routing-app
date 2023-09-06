package pl.jacekrys.routingapp.feature.route.data.remote

import pl.jacekrys.routingapp.feature.route.data.remote.model.RouteDetailsResponse
import retrofit2.http.GET

interface RoutingApi {
    @GET("/mobile/routes")
    suspend fun getOptimalRoute(): RouteDetailsResponse
}