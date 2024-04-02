package dev.wiskiw.openmeteoforecastprovider.data.remote

import dev.wiskiw.openmeteoforecastprovider.data.remote.response.OpenMeteoForecaseResponse
import dev.wiskiw.shared.model.LatLng

internal interface OpenMeteoRemoteService {
    suspend fun getForecast(latLng: LatLng): OpenMeteoForecaseResponse
}
