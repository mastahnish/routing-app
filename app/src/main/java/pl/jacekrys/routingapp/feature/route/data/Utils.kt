package pl.jacekrys.routingapp.feature.route.data

fun mapListOfCoordinatesToQueryParameter(coordinates: List<pl.jacekrys.routingapp.feature.route.domain.model.Coordinates>): String {
    return coordinates.joinToString(separator = "|") { "${it.latitude},${it.longitude}" }
}