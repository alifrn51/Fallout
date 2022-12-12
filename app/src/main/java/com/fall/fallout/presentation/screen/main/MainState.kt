package com.fall.fallout.presentation.screen.main

import com.fall.fallout.domain.model.Person
import com.fall.fallout.presentation.component.MapStyle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties

data class MainState(
    val mapProperties: MapProperties = MapProperties(
        mapStyleOptions = MapStyleOptions(MapStyle.json),
        isMyLocationEnabled = true,
    ),
    val persons:List<Person> = emptyList(),
    val latLong: LatLng? = null

)
