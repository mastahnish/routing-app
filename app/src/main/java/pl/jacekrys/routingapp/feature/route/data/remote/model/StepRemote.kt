package pl.jacekrys.routingapp.feature.route.data.remote.model

import com.squareup.moshi.Json

data class StepRemote(
    val distance: Int,
    @Json(name = "from_index") val fromIndex: Int,
    val instruction: InstructionRemote,
    val time: Double,
    @Json(name = "to_index") val toIndex: Int,
    val toll: Boolean
)