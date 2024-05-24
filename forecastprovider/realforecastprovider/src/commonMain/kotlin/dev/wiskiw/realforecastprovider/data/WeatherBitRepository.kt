package dev.wiskiw.realforecastprovider.data

import dev.wiskiw.common.data.ForecastRepository
import dev.wiskiw.common.data.model.DataLayerException
import dev.wiskiw.common.data.model.LatLng
import dev.wiskiw.common.data.model.Response
import dev.wiskiw.common.data.wrapWithResponse
import dev.wiskiw.realforecastprovider.data.remote.weatherbit.WeatherBitRemoteService

internal class WeatherBitRepository(
    private val weatherService: WeatherBitRemoteService,
) : ForecastRepository {
    override suspend fun getTemperature(latLng: LatLng): Response<Float> = wrapWithResponse {
        val forecastResponse = weatherService.getWeather(latLng)
        val data = forecastResponse.dataList.firstOrNull()
            ?: throw DataLayerException(DataLayerException.Type.SERVER_ERROR, "Data list is empty")

        return@wrapWithResponse data.temp.toFloat()
    }
}
