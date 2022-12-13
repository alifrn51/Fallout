package com.fall.fallout.presentation.screen.main

import com.google.android.gms.maps.model.LatLng

sealed class MainEvent{

    data class UpdateLocation(var latLong: LatLng): MainEvent()

    data class SensorActivation(var isEnable: Boolean): MainEvent()

    data class ContactActivation(var isEnable: Boolean): MainEvent()

    object SendSMS: MainEvent()
}
