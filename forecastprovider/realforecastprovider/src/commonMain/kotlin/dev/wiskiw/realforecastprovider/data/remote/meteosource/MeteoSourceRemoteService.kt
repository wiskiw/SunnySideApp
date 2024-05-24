package dev.wiskiw.realforecastprovider.data.remote.meteosource

import dev.wiskiw.common.data.model.LatLng
import dev.wiskiw.realforecastprovider.data.remote.openweathermap.response.OpenWeatherMapWeatherResponse

internal interface MeteoSourceRemoteService {
    suspend fun getWeather(latLng: LatLng): MeteoSourceWeatherResponse
}
