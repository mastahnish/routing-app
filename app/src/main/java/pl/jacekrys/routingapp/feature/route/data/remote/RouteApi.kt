package pl.jacekrys.routingapp.feature.route.data.remote

import pl.jacekrys.routingapp.feature.route.data.remote.model.RouteRemote
import pl.jacekrys.routingapp.feature.route.data.remote.model.RoutesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RouteApi {
    @GET("/mobile/routes")
    suspend fun getRoutes(): RoutesResponse

    @GET("/mobile/routes/{id}")
    suspend fun getRoute(@Path("id") id: String): RouteRemote
}