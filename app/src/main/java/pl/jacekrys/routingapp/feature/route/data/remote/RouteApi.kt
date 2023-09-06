package pl.jacekrys.routingapp.feature.route.data.remote

import pl.jacekrys.routingapp.feature.route.data.remote.model.RoutesResponse
import retrofit2.http.GET

interface RouteApi {
    @GET("/mobile/routes")
    suspend fun getRoutes(): RoutesResponse
}