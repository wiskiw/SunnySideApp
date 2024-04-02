package dev.wiskiw.sunnysideapp.domain.repository

import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.sunnysideapp.data.model.Response

interface WeatherRepository {
    suspend fun getTemperature(location: LatLng): Response<Float>

}
