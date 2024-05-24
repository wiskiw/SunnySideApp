package dev.wiskiw.realforecastprovider.data.remote.openmeteoweather.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OpenMeteoForecastResponse(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,

    @SerialName("current_weather")
    val currentWeather: CurrentWeather,
) {

    @Serializable
    data class CurrentWeather(
        @SerialName("temperature")
        val temperature: Float,
    )
}
