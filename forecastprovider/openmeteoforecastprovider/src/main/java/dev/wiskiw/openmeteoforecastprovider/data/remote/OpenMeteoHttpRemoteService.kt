package dev.wiskiw.openmeteoforecastprovider.data.remote

import dev.wiskiw.openmeteoforecastprovider.data.remote.response.OpenMeteoForecaseResponse
import dev.wiskiw.shared.model.LatLng
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class OpenMeteoHttpRemoteService(
    private val jsonClient: HttpClient,
) : OpenMeteoRemoteService {

    override suspend fun getForecast(latLng: LatLng): OpenMeteoForecaseResponse = jsonClient
        .get("https://api.open-meteo.com/v1/forecast") {
            parameter("latitude", latLng.latitude)
            parameter("longitude", latLng.longitude)
            parameter("hourly", "temperature_2m")
            parameter("forecast_days", 1)
        }
        .body()
}
