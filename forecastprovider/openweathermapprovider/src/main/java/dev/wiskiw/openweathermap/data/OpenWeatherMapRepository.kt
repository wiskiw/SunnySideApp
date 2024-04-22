package dev.wiskiw.openweathermap.data

import dev.wiskiw.openweathermap.data.remote.OpenWeatherMapService
import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.shared.model.Response
import kotlinx.coroutines.delay
import javax.inject.Inject

internal class OpenWeatherMapRepository @Inject constructor(
    private val weatherService: OpenWeatherMapService,
) : ForecastRepository {
    override suspend fun getTemperature(latLng: LatLng): Response<Float> {
        // TODO wrap with Try/Catch
        val forecastResponse = weatherService.getWeather(latLng)
        val temperature = forecastResponse.main.temp.toFloat()
        return temperature.let { Response.Success(it) }
    }
}