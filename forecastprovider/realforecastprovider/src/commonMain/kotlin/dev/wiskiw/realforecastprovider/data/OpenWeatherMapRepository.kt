package dev.wiskiw.realforecastprovider.data

import dev.wiskiw.common.data.ForecastRepository
import dev.wiskiw.common.data.model.LatLng
import dev.wiskiw.common.data.model.Response
import dev.wiskiw.common.data.wrapWithResponse
import dev.wiskiw.realforecastprovider.data.remote.openweathermap.OpenWeatherMapRemoteService

internal class OpenWeatherMapRepository(
    private val weatherService: OpenWeatherMapRemoteService,
) : ForecastRepository {
    override suspend fun getTemperature(latLng: LatLng): Response<Float> = wrapWithResponse {
        val forecastResponse = weatherService.getWeather(latLng)
        return@wrapWithResponse forecastResponse.main.temp.toFloat()
    }
}