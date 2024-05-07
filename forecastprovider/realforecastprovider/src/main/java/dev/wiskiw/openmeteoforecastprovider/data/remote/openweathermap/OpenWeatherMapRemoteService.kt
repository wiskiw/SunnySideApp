package dev.wiskiw.openmeteoforecastprovider.data.remote.openweathermap

import dev.wiskiw.openmeteoforecastprovider.data.remote.openweathermap.response.OpenWeatherMapWeatherResponse
import dev.wiskiw.shared.model.LatLng

internal interface OpenWeatherMapRemoteService {
    suspend fun getWeather(latLng: LatLng): OpenWeatherMapWeatherResponse
}
