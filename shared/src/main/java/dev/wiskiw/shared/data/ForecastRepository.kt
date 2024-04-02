package dev.wiskiw.shared.data

import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.shared.model.Response

interface ForecastRepository {

    /**
     * Return a temperature in celsius at location [location] at the current moment.
     */
    suspend fun getTemperature(location: LatLng): Response<Float>
}
