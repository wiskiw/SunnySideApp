package dev.wiskiw.sunnysideapp.data

import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.shared.data.model.Response
import dev.wiskiw.sunnysideapp.data.remote.OpenMeteoService
import dev.wiskiw.shared.data.ForecastRepository
import javax.inject.Inject

class OpenMeteoWeatherRepository @Inject constructor(
    private val openMeteoService: OpenMeteoService,
) : ForecastRepository {
    override suspend fun getTemperature(location: LatLng): Response<Float> {
        val forecastResponse = openMeteoService.getForecast(location)
        val temperature = forecastResponse.hourly.temperatures.firstOrNull()

        return temperature
            ?.let { Response.Success(it) }
            ?: Response.Failure(IllegalArgumentException("Null temperature"))
    }
}