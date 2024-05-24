package dev.wiskiw.realforecastprovider.data

import dev.wiskiw.common.data.ForecastRepository
import dev.wiskiw.common.data.model.DataLayerException
import dev.wiskiw.common.data.model.LatLng
import dev.wiskiw.common.data.model.Response
import dev.wiskiw.common.data.wrapWithResponse
import dev.wiskiw.realforecastprovider.data.remote.meteosource.MeteoSourceRemoteService
import dev.wiskiw.realforecastprovider.data.remote.weatherbit.WeatherBitRemoteService

internal class MeteoSourceRepository(
    private val weatherService: MeteoSourceRemoteService,
) : ForecastRepository {
    override suspend fun getTemperature(latLng: LatLng): Response<Float> = wrapWithResponse {
        val response = weatherService.getWeather(latLng)
        return@wrapWithResponse response.current.temperature.toFloat()
    }
}
