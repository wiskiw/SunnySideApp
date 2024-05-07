package dev.wiskiw.openmeteoforecastprovider.data.remote.openweathermap.response

import kotlinx.serialization.Serializable

@Serializable
internal data class OpenWeatherMapWeatherResponse(
    val main: Main,
) {
    @Serializable
    data class Main(
        val temp: Double,
    )
}
