package dev.wiskiw.realforecastprovider.data.remote.meteosource

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MeteoSourceWeatherResponse(
    @SerialName("current")
    val current: Current,
) {
    @Serializable
    data class Current(
        val temperature: Double,
    )
}
