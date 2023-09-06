package pl.jacekrys.routingapp.feature.route.data.remote

import pl.jacekrys.routingapp.BuildConfig
import pl.jacekrys.routingapp.feature.route.data.remote.model.RouteDetailsResponse
import pl.jacekrys.routingapp.feature.route.domain.model.Coordinates
import retrofit2.http.GET
import retrofit2.http.Query

interface RoutingApi {
    @GET("/v1/routing")
    suspend fun getOptimalRoute(
        @Query("waypoints") waypoints: String,
        @Query("mode") mode: String = "drive",
        @Query("apiKey") apiKey: String = BuildConfig.ROUTING_API_KEY,
        @Query("format") format: String = "json",
    ): RouteDetailsResponse
}