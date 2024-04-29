package dev.wiskiw.openmeteoforecastprovider.data

import dev.wiskiw.openmeteoforecastprovider.data.remote.OpenMeteoRemoteService
import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.shared.model.Response
import dev.wiskiw.shared.utils.wrapWithResponse
import javax.inject.Inject

internal class OpenMeteoWeatherRepository @Inject constructor(
    private val openMeteoRemoteService: OpenMeteoRemoteService,
) : ForecastRepository {
    override suspend fun getTemperature(latLng: LatLng): Response<Float> = wrapWithResponse {
        val forecastResponse = openMeteoRemoteService.getForecast(latLng)
        val temperature = forecastResponse.hourly.temperatures.firstOrNull()
        return@wrapWithResponse temperature ?: throw IllegalArgumentException("Null temperature")
    }
}