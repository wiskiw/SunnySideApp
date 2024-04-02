package dev.wiskiw.shared.data

import dev.wiskiw.shared.data.model.Response
import dev.wiskiw.shared.model.LatLng

interface ForecastRepository {
    suspend fun getTemperature(location: LatLng): Response<Float>
}
