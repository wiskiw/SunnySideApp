package dev.wiskiw.shared.data.service

import org.koin.core.scope.Scope

actual fun Scope.createNativeGeocoderService(): GeocoderService = AndroidGeocoderService(get())

actual fun Scope.createFusedLocationService(): LocationService = FusedLocationService(get())
