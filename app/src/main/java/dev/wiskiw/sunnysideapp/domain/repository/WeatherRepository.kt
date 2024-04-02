package dev.wiskiw.sunnysideapp.domain.repository

import dev.wiskiw.sunnysideapp.common.model.LatLng
import dev.wiskiw.sunnysideapp.data.model.Response

interface WeatherRepository {
    suspend fun getTemperature(location: LatLng): Response<Float>

}
