package dev.wiskiw.realforecastprovider.data.remote.weatherbit

import dev.wiskiw.common.data.model.LatLng
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path

internal class WeatherBitHttpRemoteService(
    private val httpClient: HttpClient,
    private val apiKey: String,
) : WeatherBitRemoteService {

    companion object {
        private const val BASE_URL = "api.weatherbit.io/v2.0"
    }

    private fun HttpRequestBuilder.commonRequest() {
        url {
            host = BASE_URL
        }
        parameter("key", apiKey)
    }

    override suspend fun getWeather(latLng: LatLng): WeatherBitWeatherResponse = httpClient
        .get {
            commonRequest()
            url {
                path("current")
            }

            parameter("lat", latLng.latitude)
            parameter("lon", latLng.longitude)
        }
        .body()
}
