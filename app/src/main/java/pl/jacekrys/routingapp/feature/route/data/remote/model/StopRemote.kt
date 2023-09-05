package pl.jacekrys.routingapp.feature.route.data.remote.model

data class StopRemote(
    val coord: CoordRemote,
    val id: String,
    val students: List<StudentRemote>,
    val type: String
)