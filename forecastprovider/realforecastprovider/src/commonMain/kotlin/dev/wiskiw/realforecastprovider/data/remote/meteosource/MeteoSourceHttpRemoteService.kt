package dev.wiskiw.realforecastprovider.data.remote.meteosource

import dev.wiskiw.common.data.model.LatLng
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path

internal class MeteoSourceHttpRemoteService(
    private val httpClient: HttpClient,
    private val apiKey: String,
) : MeteoSourceRemoteService {

    companion object {
        private const val BASE_URL = "www.meteosource.com/api/v1/free"
    }

    private fun HttpRequestBuilder.commonRequest() {
        url {
            host = BASE_URL
        }
        parameter("key", apiKey)
    }

    override suspend fun getWeather(latLng: LatLng): MeteoSourceWeatherResponse = httpClient
        .get {
            commonRequest()
            url {
                path("point")
            }

            parameter("lat", latLng.latitude)
            parameter("lon", latLng.longitude)
            parameter("sections", "current")
            parameter("language", "en")
            parameter("units", "metric")
        }
        .body()
}
