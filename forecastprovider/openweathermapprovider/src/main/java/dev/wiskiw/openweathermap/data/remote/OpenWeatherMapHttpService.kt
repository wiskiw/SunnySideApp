package dev.wiskiw.openweathermap.data.remote

import dev.wiskiw.openweathermap.data.remote.response.WeatherResponse
import dev.wiskiw.shared.model.LatLng
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class OpenWeatherMapHttpService(
    private val jsonClient: HttpClient,
) : OpenWeatherMapService {

    override suspend fun getWeather(latLng: LatLng): WeatherResponse = jsonClient
        .get("weather") {
            parameter("lat", latLng.latitude)
            parameter("lon", latLng.longitude)
            parameter("units", "metric")
        }
        .body()
}
