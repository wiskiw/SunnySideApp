package dev.wiskiw.realforecastprovider.data.remote.weatherbit

import dev.wiskiw.common.data.model.LatLng
import dev.wiskiw.realforecastprovider.data.remote.openweathermap.response.OpenWeatherMapWeatherResponse

internal interface WeatherBitRemoteService {
    suspend fun getWeather(latLng: LatLng): WeatherBitWeatherResponse
}
