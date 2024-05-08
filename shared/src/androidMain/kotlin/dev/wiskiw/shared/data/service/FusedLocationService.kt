package dev.wiskiw.shared.data.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import dev.wiskiw.shared.data.model.LatLng
import dev.wiskiw.shared.utils.toLatLng
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class FusedLocationService(
    private val application: Application,
) : LocationService {

    @SuppressLint("MissingPermission") // linter doesn't work properly with KMP
    override suspend fun getLocation(): LatLng {
        val locationPermissionStatus =
            ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (locationPermissionStatus == PackageManager.PERMISSION_GRANTED) {

            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
            return suspendCancellableCoroutine { continuation ->
                fusedLocationClient.lastLocation.apply {
                    if (isComplete) {
                        if (isSuccessful) {
                            continuation.resume(result.toLatLng())
                        } else {
                            continuation.resumeWithException(IllegalArgumentException("Failed to fetch location"))
                        }
                    }
                    addOnSuccessListener {
                        continuation.resume(it.toLatLng())
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
