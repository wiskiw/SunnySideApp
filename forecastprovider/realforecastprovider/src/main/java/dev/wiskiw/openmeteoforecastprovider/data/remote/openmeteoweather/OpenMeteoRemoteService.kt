package dev.wiskiw.openmeteoforecastprovider.data.remote.openmeteoweather

import dev.wiskiw.openmeteoforecastprovider.data.remote.openmeteoweather.response.OpenMeteoForecastResponse
import dev.wiskiw.shared.model.LatLng

internal interface OpenMeteoRemoteService {
    suspend fun getForecast(latLng: LatLng): OpenMeteoForecastResponse
}
