package dev.wiskiw.openweathermap.data

import dev.wiskiw.openweathermap.data.remote.OpenWeatherMapService
import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.shared.model.Response
import dev.wiskiw.shared.utils.wrapWithResponse

internal class OpenWeatherMapRepository(
    private val weatherService: OpenWeatherMapService,
) : ForecastRepository {
    override suspend fun getTemperature(latLng: LatLng): Response<Float> = wrapWithResponse {
        val forecastResponse = weatherService.getWeather(latLng)
        return@wrapWithResponse forecastResponse.main.temp.toFloat()
    }
}