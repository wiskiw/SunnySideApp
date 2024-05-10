package dev.wiskiw.shared.data.service

import android.app.Application
import android.location.Geocoder
import dev.wiskiw.common.data.model.LatLng
import io.github.aakira.napier.Napier
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.IOException
import java.util.Locale
import kotlin.coroutines.resume


class AndroidGeocoderService(
    private val application: Application,
) : GeocoderService {

    override suspend fun getName(location: LatLng): String? {
        val geocoder = Geocoder(application, Locale.getDefault())

        return suspendCancellableCoroutine { continuation ->
            try {
                val geocoderAddresses = geocoder
                    .getFromLocation(location.latitude, location.longitude, 1)
                    ?: emptyList()
                val resultAddress = geocoderAddresses.firstOrNull()?.locality
                continuation.resume(resultAddress)
            } catch (e: IOException) {
                Napier.v("Exception during fetching location name", e)
                continuation.resume(null)
            }
        }
    }
}
