package dev.wiskiw.shared.data

import dev.wiskiw.shared.data.model.Response
import dev.wiskiw.shared.data.model.LatLng

interface ForecastRepository {

    /**
     * Return a temperature in celsius at location [latLng] at the current moment.
     */
    suspend fun getTemperature(latLng: LatLng): Response<Float>
}
