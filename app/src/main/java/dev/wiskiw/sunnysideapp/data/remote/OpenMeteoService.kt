package dev.wiskiw.sunnysideapp.data.remote

import dev.wiskiw.sunnysideapp.common.model.LatLng
import dev.wiskiw.sunnysideapp.data.remote.model.reponse.OpenMeteoForecaseResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class OpenMeteoService @Inject constructor(
    private val jsonClient: HttpClient,
) {
    suspend fun getForecast(latLng: LatLng): OpenMeteoForecaseResponse = jsonClient
        .get("https://api.open-meteo.com/v1/forecast") {
            parameter("latitude", latLng.latitude)
            parameter("longitude", latLng.longitude)
            parameter("hourly", "temperature_2m")
            parameter("forecast_days", 1)
        }
        .body()
}
