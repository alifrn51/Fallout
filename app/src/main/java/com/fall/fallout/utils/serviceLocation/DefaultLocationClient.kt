package com.fall.fallout.utils.serviceLocation

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.activity.result.IntentSenderRequest
import com.fall.fallout.utils.hasLocationPermission
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class DefaultLocationClient(
    private val context: Context,
    private val client: FusedLocationProviderClient,
    interval: Long
) : LocationClient {


    private var request = LocationRequest.create()
        .setInterval(interval)
        .setFastestInterval(interval)


    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(): Flow<Location> {
        return callbackFlow {
            if(!context.hasLocationPermission()) {
                throw LocationClient.LocationException("Missing location permission")
            }

            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if(!isGpsEnabled && !isNetworkEnabled) {
                throw LocationClient.LocationException("GPS is disabled")
            }


            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let { location ->
                        launch { send(location) }
                    }
                }
            }

            client.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )

            awaitClose {
                client.removeLocationUpdates(locationCallback)
            }
        }
    }

    override fun turnOnGPS(onDisabled: (IntentSenderRequest) -> Unit): Flow<Boolean> {

        return callbackFlow {
            val client: SettingsClient = LocationServices.getSettingsClient(context)

            val builder: LocationSettingsRequest.Builder = LocationSettingsRequest
                .Builder()
                .addLocationRequest(request)


            val gpsSettingTask: Task<LocationSettingsResponse> =
                client.checkLocationSettings(builder.build())

            gpsSettingTask.addOnSuccessListener {
                launch { send(true) }
            }
            gpsSettingTask.addOnFailureListener { exception ->

                launch { send(false) }

                if (exception is ResolvableApiException) {
                    try {
                        val intentSenderRequest = IntentSenderRequest
                            .Builder(exception.resolution)
                            .build()
                        onDisabled(intentSenderRequest)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // ignore here
                    }
                }
            }
            awaitClose {
            }


        }



    }


}