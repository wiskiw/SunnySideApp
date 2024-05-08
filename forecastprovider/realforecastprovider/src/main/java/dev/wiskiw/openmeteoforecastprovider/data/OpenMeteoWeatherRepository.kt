package dev.wiskiw.openmeteoforecastprovider.data

import dev.wiskiw.openmeteoforecastprovider.data.remote.openmeteoweather.OpenMeteoRemoteService
import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.domain.model.LatLng
import dev.wiskiw.shared.data.model.Response
import dev.wiskiw.shared.data.wrapWithResponse

internal class OpenMeteoWeatherRepository(
    private val openMeteoService: OpenMeteoRemoteService,
) : dev.wiskiw.shared.data.ForecastRepository {
    override suspend fun getTemperature(latLng: dev.wiskiw.shared.domain.model.LatLng): dev.wiskiw.shared.data.model.Response<Float> =
        dev.wiskiw.shared.data.wrapWithResponse {
            val forecastResponse = openMeteoService.getForecast(latLng)
            val temperature = forecastResponse.hourly.temperatures.firstOrNull()
            return@wrapWithResponse temperature ?: throw IllegalArgumentException("Null temperature")
        }
}