package dev.wiskiw.openweathermap.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val main: Main,
) {
    @Serializable
    data class Main(
        val temp: Double,
    )
}
