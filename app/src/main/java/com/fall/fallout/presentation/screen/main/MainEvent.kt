package com.fall.fallout.presentation.screen.main

import com.google.android.gms.maps.model.LatLng

sealed class MainEvent{

    data class UpdateLocation(var latLong: LatLng): MainEvent()

    data class CurrentLocation(var latLong: LatLng) : MainEvent()

}
