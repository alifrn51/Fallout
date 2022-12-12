package com.fall.fallout.presentation.screen.main

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import com.fall.fallout.presentation.component.BoxSendingListActivation
import com.fall.fallout.presentation.component.ButtonBluer
import com.fall.fallout.presentation.screen.destinations.PersonListScreenDestination
import com.fall.fallout.presentation.screen.destinations.SettingsScreenDestination
import com.fall.fallout.ui.theme.BETWEEN_PADDING
import com.fall.fallout.ui.theme.FalloutTheme
import com.fall.fallout.ui.theme.SMALL_PADDING
import com.fall.fallout.utils.isPermanentlyDenied
import com.fall.fallout.utils.serviceLocation.DefaultLocationClient
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalPermissionsApi
@RootNavGraph(start = true)
@Destination
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {


    val state = viewModel.state.value

    val switchON = remember {
        mutableStateOf(false)
    }

    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false, myLocationButtonEnabled = false)
    }

    var permissionFindLocationGranted by remember {
        mutableStateOf(false)
    }

    var permissionCoarseLocationGranted by remember {
        mutableStateOf(false)
    }

    val gpsState = remember {
        mutableStateOf(false)
    }

    val context: Context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()


    val cameraPositionState = rememberCameraPositionState()

    val locationClient = DefaultLocationClient(
        context = context,
        LocationServices.getFusedLocationProviderClient(context),
        interval = 3000L
    )

    val settingResultRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        gpsState.value = activityResult.resultCode == RESULT_OK
    }


    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    permissionState.launchMultiplePermissionRequest()

                    if (permissionFindLocationGranted &&
                            permissionCoarseLocationGranted) {
                            locationClient.turnOnGPS { intentSenderRequest ->

                                settingResultRequest.launch(intentSenderRequest)

                            }.onEach { enable ->
                                gpsState.value = enable
                            }.launchIn(coroutineScope)

                    }

                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )




    permissionState.permissions.forEach { prem ->


        when (prem.permission) {

            Manifest.permission.ACCESS_COARSE_LOCATION -> {

                when {
                    prem.hasPermission -> {

                        permissionCoarseLocationGranted = prem.hasPermission


                    }

                    prem.shouldShowRationale -> {


                    }

                    prem.isPermanentlyDenied() -> {

                    }
                }

            }

            Manifest.permission.ACCESS_FINE_LOCATION -> {


                when {
                    prem.hasPermission -> {

                        permissionFindLocationGranted = prem.hasPermission


                    }

                    prem.shouldShowRationale -> {


                    }

                    prem.isPermanentlyDenied() -> {

                    }
                }


            }
        }


    }



    Box(
        modifier = Modifier.fillMaxSize()
    ) {


        if (permissionCoarseLocationGranted &&
            permissionFindLocationGranted
        ) {


            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            if (gpsState.value || isGpsEnabled) {

                rememberCoroutineScope {

                    locationClient.getLocationUpdates()
                        .catch { e -> e.printStackTrace() }
                        .onEach { location ->

                            viewModel.onEvent(
                                MainEvent.UpdateLocation(
                                    LatLng(location.latitude, location.longitude)
                                )
                            )

                        }.launchIn(lifecycleOwner.lifecycleScope)
                }

            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = state.mapProperties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState,
            )

        }







        state.latLong?.let {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                LatLng(
                    it.latitude,
                    it.longitude
                ), 16f
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SMALL_PADDING)
                .align(Alignment.BottomCenter)
        ) {


            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {

                IconButton(onClick = {


                    /*Intent(context, LocationService::class.java).apply {
                        action = LocationService.ACTION_START
                        context.startService(this)
                    }*/

                    if (!gpsState.value){
                        coroutineScope.launch {


                            locationClient.turnOnGPS { intentSenderRequest ->

                                settingResultRequest.launch(intentSenderRequest)

                            }.onEach { enable ->
                                gpsState.value = enable
                            }.launchIn(this)

                        }
                    }else {
                        state.latLong?.let {
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                                LatLng(
                                    it.latitude,
                                    it.longitude
                                ), 16f
                            )
                        }
                    }



                }) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(shape = RoundedCornerShape(100.dp))
                            .background(color = MaterialTheme.colors.surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MyLocation,
                            contentDescription = "Location",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(BETWEEN_PADDING))
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {

                ButtonBluer(
                    modifier = Modifier.weight(0.5f),
                    icon = Icons.Default.Person,
                    title = "Person List"
                ) {
                    navigator.navigate(PersonListScreenDestination)
                }
                Spacer(modifier = Modifier.width(BETWEEN_PADDING))
                ButtonBluer(
                    modifier = Modifier.weight(0.5f),
                    icon = Icons.Default.Settings,
                    title = "Settings"
                ) {
                    navigator.navigate(SettingsScreenDestination)
                }

            }

            Spacer(modifier = Modifier.height(BETWEEN_PADDING))

            BoxSendingListActivation(listPerson = state.persons, switchON = switchON)
        }

    }


}


@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {

    FalloutTheme {
    }
}