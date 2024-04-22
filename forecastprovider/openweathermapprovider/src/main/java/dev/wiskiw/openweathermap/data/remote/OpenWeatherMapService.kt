package dev.wiskiw.openweathermap.data.remote

import dev.wiskiw.openweathermap.data.remote.response.WeatherResponse
import dev.wiskiw.shared.model.LatLng

internal interface OpenWeatherMapService {
    suspend fun getWeather(latLng: LatLng): WeatherResponse
}
