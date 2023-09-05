package pl.jacekrys.routingapp.feature.route.data.remote.model

data class PropertiesRemote(
    val mode: String,
    val units: String,
    val waypoints: List<WaypointRemote>
)