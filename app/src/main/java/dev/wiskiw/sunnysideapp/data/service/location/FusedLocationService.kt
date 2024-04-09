package dev.wiskiw.sunnysideapp.data.service.location

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FusedLocationService(
    private val application: Application,
) : LocationService {

    override suspend fun getLocation(): Location {
        val locationPermissionStatus =
            ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (locationPermissionStatus == PackageManager.PERMISSION_GRANTED) {

            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
            return suspendCancellableCoroutine { continuation ->
                fusedLocationClient.lastLocation.apply {
                    if (isComplete) {
                        if (isSuccessful) {
                            continuation.resume(result)
                        } else {
                            continuation.resumeWithException(IllegalArgumentException("Failed to fetch location"))
                        }
                    }
                    addOnSuccessListener {
                        continuation.resume(it)
                    }
                    addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
                    addOnCanceledListener {
                        continuation.cancel()
                    }
                }
            }
        } else {
            throw IllegalStateException("Permission ACCESS_COARSE_LOCATION were not granted")
        }
    }
}
