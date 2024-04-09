package dev.wiskiw.sunnysideapp.domain.usecase

import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

class LocationAddressUseCase @Inject constructor(
    private val application: Application,
) {

    companion object {
        private const val LOG_TAG = "LocationNameUC"
    }

    suspend fun getAddress(location: Location): Address? {
        val geocoder = Geocoder(application, Locale.getDefault())

        return try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1) ?: emptyList()
            addresses.firstOrNull()
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Exception during fetching city name", e)
            null
        }

    }

}