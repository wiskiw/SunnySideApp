package dev.wiskiw.realforecastprovider.data.remote.openweathermap

import dev.wiskiw.common.data.model.LatLng
import dev.wiskiw.realforecastprovider.data.remote.openweathermap.response.OpenWeatherMapWeatherResponse

internal interface OpenWeatherMapRemoteService {
    suspend fun getWeather(latLng: LatLng): OpenWeatherMapWeatherResponse
}
