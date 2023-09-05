package pl.jacekrys.routingapp.feature.route.data.remote.model

import com.squareup.moshi.Json

data class WaypointRemoteX(
    val location: List<Double>,
    @Json(name = "original_index") val originalIndex: Int
)