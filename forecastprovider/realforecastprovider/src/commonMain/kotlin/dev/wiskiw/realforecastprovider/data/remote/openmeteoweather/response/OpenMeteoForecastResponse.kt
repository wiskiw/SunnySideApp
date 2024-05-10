package dev.wiskiw.realforecastprovider.data.remote.openmeteoweather.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OpenMeteoForecastResponse(
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