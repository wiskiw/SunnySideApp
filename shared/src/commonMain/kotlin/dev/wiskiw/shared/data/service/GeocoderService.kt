package dev.wiskiw.shared.data.service

import dev.wiskiw.shared.data.model.LatLng


interface GeocoderService {
    suspend fun getName(location: LatLng): String?
}
