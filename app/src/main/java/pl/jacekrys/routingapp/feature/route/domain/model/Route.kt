package pl.jacekrys.routingapp.feature.route.domain.model

data class Route(
    val id: String,
    val name: String,
    val stops: List<Stop>?
)
