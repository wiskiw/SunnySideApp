package dev.wiskiw.openmeteoforecastprovider.data

import dev.wiskiw.openmeteoforecastprovider.data.remote.openweathermap.OpenWeatherMapRemoteService
import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.domain.model.LatLng
import dev.wiskiw.shared.data.model.Response
import dev.wiskiw.shared.data.wrapWithResponse

internal class OpenWeatherMapRepository(
    private val weatherService: OpenWeatherMapRemoteService,
) : dev.wiskiw.shared.data.ForecastRepository {
    override suspend fun getTemperature(latLng: dev.wiskiw.shared.domain.model.LatLng): dev.wiskiw.shared.data.model.Response<Float> =
        dev.wiskiw.shared.data.wrapWithResponse {
            val forecastResponse = weatherService.getWeather(latLng)
            return@wrapWithResponse forecastResponse.main.temp.toFloat()
        }
}