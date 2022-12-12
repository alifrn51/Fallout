package com.fall.fallout.utils.serviceLocation

import android.location.Location
import androidx.activity.result.IntentSenderRequest
import kotlinx.coroutines.flow.Flow

interface LocationClient {

    fun getLocationUpdates(): Flow<Location>
    fun turnOnGPS(onDisabled: (IntentSenderRequest) -> Unit): Flow<Boolean>
    class LocationException(message: String): Exception()
}