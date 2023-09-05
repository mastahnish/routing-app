package pl.jacekrys.routingapp.feature.route.data.remote.model

data class RouteRemote(
    val id: String,
    val name: String,
    val stops: List<StopRemote>
)