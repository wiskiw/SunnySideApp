package dev.wiskiw.realforecastprovider.data.remote.openmeteoweather

import dev.wiskiw.common.data.model.LatLng
import dev.wiskiw.realforecastprovider.data.remote.openmeteoweather.response.OpenMeteoForecastResponse

internal interface OpenMeteoRemoteService {
    suspend fun getForecast(latLng: LatLng): OpenMeteoForecastResponse
}
