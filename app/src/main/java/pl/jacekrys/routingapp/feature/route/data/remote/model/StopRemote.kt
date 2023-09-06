package pl.jacekrys.routingapp.feature.route.data.remote.model

import pl.jacekrys.routingapp.feature.route.domain.model.Stop

data class StopRemote(
    val coord: CoordRemote,
    val id: String,
    val students: List<StudentRemote>,
    val type: String
) {
    fun toDomain(): Stop = Stop(
        id, coord.toDomain()
    )
}