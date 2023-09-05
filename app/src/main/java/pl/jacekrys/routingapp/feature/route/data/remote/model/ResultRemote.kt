package pl.jacekrys.routingapp.feature.route.data.remote.model

import com.squareup.moshi.Json

data class ResultRemote(
    val distance: Int,
    @Json(name = "distance_units")val distanceUnits: String,
    val geometry: List<List<GeometryRemote>>,
    val legs: List<LegRemote>,
    val mode: String,
    val time: Double,
    val toll: Boolean,
    val units: String,
    val waypoints: List<WaypointRemoteX>
)