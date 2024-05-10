package dev.wiskiw.common.data

import dev.wiskiw.common.data.model.LatLng
import dev.wiskiw.common.data.model.Response

interface ForecastRepository {

    /**
     * Return a temperature in celsius at location [latLng] at the current moment.
     */
    suspend fun getTemperature(latLng: LatLng): Response<Float>
}
