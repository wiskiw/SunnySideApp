package dev.wiskiw.sunnysideapp.data.remote.model.reponse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenMeteoForecaseResponse(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val hourly: HourlyDto,
) {

    @Serializable
    data class HourlyDto(
        @SerialName("temperature_2m")
        val temperatures: List<Float>,
    )
}
