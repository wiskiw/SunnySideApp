package dev.wiskiw.openmeteoforecastprovider.data.remote.openweathermap

import dev.wiskiw.openmeteoforecastprovider.data.remote.openweathermap.response.OpenWeatherMapWeatherResponse
import dev.wiskiw.shared.domain.model.LatLng
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path

internal class OpenWeatherMapHttpRemoteService(
    private val httpClient: HttpClient,
    private val apiKey: String,
) : OpenWeatherMapRemoteService {

    companion object {
        private const val BASE_URL = "api.openweathermap.org/data/2.5"
    }

    private fun HttpRequestBuilder.commonRequest() {
        url {
            host = BASE_URL
        }
        parameter("appid", apiKey)
    }

    override suspend fun getWeather(latLng: dev.wiskiw.shared.domain.model.LatLng): OpenWeatherMapWeatherResponse = httpClient
        .get {
            commonRequest()
            url {
                path("weather")
            }

            parameter("lat", latLng.latitude)
            parameter("lon", latLng.longitude)
            parameter("units", "metric")
        }
        .body()
}
