package dev.wiskiw.shared.utils

import dev.wiskiw.common.data.model.LatLng
import android.location.Location as AndroidLocation


fun AndroidLocation.toLatLng() = LatLng(
    latitude = this.latitude,
    longitude = this.longitude,
)
