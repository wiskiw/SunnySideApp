package dev.wiskiw.shared.data.service

import org.koin.core.scope.Scope

expect fun Scope.createNativeGeocoderService(): GeocoderService

expect fun Scope.createFusedLocationService(): LocationService
