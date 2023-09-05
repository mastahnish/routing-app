package pl.jacekrys.routingapp.feature.route.data.remote.model

data class LegRemote(
    val distance: Int,
    val steps: List<StepRemote>,
    val time: Double
)