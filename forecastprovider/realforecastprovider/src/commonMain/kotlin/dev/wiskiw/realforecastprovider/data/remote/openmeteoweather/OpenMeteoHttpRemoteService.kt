package dev.wiskiw.realforecastprovider.data.remote.openmeteoweather

import dev.wiskiw.common.data.model.LatLng
import dev.wiskiw.realforecastprovider.data.remote.openmeteoweather.response.OpenMeteoForecastResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path

internal class OpenMeteoHttpRemoteService(
    private val httpClient: HttpClient,
) : OpenMeteoRemoteService {

    companion object {
        private const val BASE_URL = "api.open-meteo.com/v1"
    }

    private fun HttpRequestBuilder.commonRequest() {
        url {
            host = BASE_URL
        }
    }

    override suspend fun getForecast(latLng: LatLng): OpenMeteoForecastResponse = httpClient
        .get {
            commonRequest()
            url {
                path("forecast")
            }

            parameter("latitude", latLng.latitude)
            parameter("longitude", latLng.longitude)
            parameter("hourly", "temperature_2m")
            parameter("forecast_days", 1)
        }
        .body()
}
