package dev.wiskiw.realforecastprovider.data

import dev.wiskiw.common.data.ForecastRepository
import dev.wiskiw.common.data.model.LatLng
import dev.wiskiw.common.data.model.Response
import dev.wiskiw.common.data.wrapWithResponse
import dev.wiskiw.realforecastprovider.data.remote.openmeteoweather.OpenMeteoRemoteService

internal class OpenMeteoWeatherRepository(
    private val openMeteoService: OpenMeteoRemoteService,
) : ForecastRepository {
    override suspend fun getTemperature(latLng: LatLng): Response<Float> = wrapWithResponse {
        val forecastResponse = openMeteoService.getForecast(latLng)
        val temperature = forecastResponse.hourly.temperatures.firstOrNull()
        return@wrapWithResponse temperature ?: throw IllegalArgumentException("Null temperature")
    }
}