package dev.wiskiw.openmeteoforecastprovider.data.remote.openmeteoweather

import dev.wiskiw.openmeteoforecastprovider.data.remote.openmeteoweather.response.OpenMeteoForecastResponse
import dev.wiskiw.shared.domain.model.LatLng

internal interface OpenMeteoRemoteService {
    suspend fun getForecast(latLng: dev.wiskiw.shared.domain.model.LatLng): OpenMeteoForecastResponse
}
