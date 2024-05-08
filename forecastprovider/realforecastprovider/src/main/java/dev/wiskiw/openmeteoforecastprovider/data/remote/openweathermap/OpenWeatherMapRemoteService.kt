package dev.wiskiw.openmeteoforecastprovider.data.remote.openweathermap

import dev.wiskiw.openmeteoforecastprovider.data.remote.openweathermap.response.OpenWeatherMapWeatherResponse
import dev.wiskiw.shared.domain.model.LatLng

internal interface OpenWeatherMapRemoteService {
    suspend fun getWeather(latLng: dev.wiskiw.shared.domain.model.LatLng): OpenWeatherMapWeatherResponse
}
