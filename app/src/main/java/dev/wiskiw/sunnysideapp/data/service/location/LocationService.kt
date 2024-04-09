package dev.wiskiw.sunnysideapp.data.service.location

import android.location.Location

interface LocationService {

    suspend fun getLocation() : Location

}