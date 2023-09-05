package pl.jacekrys.routingapp.feature.route.data.remote.model

data class RouteDetailsRemote(
    val properties: PropertiesRemote,
    val results: List<ResultRemote>
)