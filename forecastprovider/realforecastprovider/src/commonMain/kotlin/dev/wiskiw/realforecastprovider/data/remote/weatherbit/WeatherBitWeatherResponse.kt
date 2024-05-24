package dev.wiskiw.realforecastprovider.data.remote.weatherbit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class WeatherBitWeatherResponse(
    @SerialName("data")
    val dataList: List<Data>,
) {
    @Serializable
    data class Data(
        val temp: Double, // Temperature

        @SerialName("app_temp")
        val appTemp: Double, // Apparent/"Feels Like" temperature (default Celsius)
    )
}
