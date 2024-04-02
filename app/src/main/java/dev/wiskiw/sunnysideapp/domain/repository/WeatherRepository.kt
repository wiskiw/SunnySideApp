package dev.wiskiw.sunnysideapp.domain.repository

import dev.wiskiw.sunnysideapp.data.model.Response

interface WeatherRepository {
    suspend fun getTemperature(): Response<Float>

}
