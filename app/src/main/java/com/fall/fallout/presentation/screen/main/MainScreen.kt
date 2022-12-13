package com.fall.fallout.presentation.screen.main

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import com.fall.fallout.R
import com.fall.fallout.presentation.component.*
import com.fall.fallout.presentation.screen.destinations.PersonListScreenDestination
import com.fall.fallout.presentation.screen.destinations.SettingsScreenDestination
import com.fall.fallout.ui.theme.BETWEEN_PADDING
import com.fall.fallout.ui.theme.FalloutTheme
import com.fall.fallout.ui.theme.SMALL_PADDING
import com.fall.fallout.utils.isPermanentlyDenied
import com.fall.fallout.utils.serviceLocation.DefaultLocationClient
import com.fall.fallout.utils.serviceLocation.FallDetectionService
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalPermissionsApi
@Destination
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {


    val showDialogFallDetect = remember { mutableStateOf(false) }
    val showDialogSendSMS = remember { mutableStateOf(false) }
    val showDialogPermissionSMS = remember { mutableStateOf(false) }
    val showDialogPermissionLocation = remember { mutableStateOf(false) }

    val isDoneSendSMS = remember { mutableStateOf(true) }

    val state = viewModel.state.value


    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false, myLocationButtonEnabled = false)
    }

    var permissionFindLocationGranted by remember {
        mutableStateOf(false)
    }

    var permissionCoarseLocationGranted by remember {
        mutableStateOf(false)
    }


    val switchSensor = remember {
        mutableStateOf(false)
    }
    val switchContact = remember {
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
    val permissionStateSMS = rememberPermissionState(
        Manifest.permission.SEND_SMS

    )

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {

                    if(FallDetectionService.fallDetection != null){
                        switchSensor.value = true
                    }
                    permissionState.launchMultiplePermissionRequest()

                    if (permissionFindLocationGranted &&
                        permissionCoarseLocationGranted
                    ) {
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
                        if (!showDialogPermissionLocation.value) {
                            Log.i("TAG", "MainScreen: locationdenisdlfas")
                            showDialogPermissionLocation.value = true
                        }
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


            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
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




        BoxSensorActivation(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = SMALL_PADDING, end = SMALL_PADDING, top = 30.dp, bottom = 0.dp)
                .align(Alignment.TopCenter),
            switchOnChange = {
                viewModel.onEvent(MainEvent.SensorActivation(it))
            },
            switch = switchSensor
        )



        FallDetectionService.fallDetection?.isFallDetect?.observe(lifecycleOwner) { isFall ->
            //Log.i("TAG", "MainScreen: Fall Shode $isFall")
            if (isFall) {

                if (!showDialogFallDetect.value && !showDialogSendSMS.value)
                    showDialogFallDetect.value = true

            }
        }






        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SMALL_PADDING)
                .align(Alignment.BottomCenter)
        ) {


            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {

                IconButton(onClick = {


                    if (!gpsState.value) {
                        coroutineScope.launch {


                            locationClient.turnOnGPS { intentSenderRequest ->

                                settingResultRequest.launch(intentSenderRequest)

                            }.onEach { enable ->
                                gpsState.value = enable
                            }.launchIn(this)

                        }
                    } else {
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

            BoxSendingListActivation(
                listPerson = state.persons,
                switchOnChange = { it ->
                    permissionStateSMS.launchPermissionRequest()

                    when {
                        permissionStateSMS.hasPermission -> {
                            switchContact.value = true
                            viewModel.onEvent(MainEvent.ContactActivation(it))

                        }

                        permissionStateSMS.shouldShowRationale -> {
                            viewModel.onEvent(MainEvent.ContactActivation(false))
                            switchContact.value = false
                        }

                        permissionStateSMS.isPermanentlyDenied() -> {
                            viewModel.onEvent(MainEvent.ContactActivation(false))
                            switchContact.value = false
                           showDialogPermissionSMS.value = true

                        }
                    }
                },
                switch = switchContact
            )
        }

    }



    state.latLong?.let {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(
            LatLng(
                it.latitude,
                it.longitude
            ), 16f
        )
    }

    state.switchSensor.let {
        Intent(context, FallDetectionService::class.java).apply {
            action = if (it) FallDetectionService.ACTION_START else FallDetectionService.ACTION_STOP
            context.startService(this)
        }

    }

    when {

        showDialogFallDetect.value -> {

            DialogFallDetect(
                title = "Delete Person",
                clickableIamOk = {
                    showDialogFallDetect.value = false
                },
                setShowDialog = { showDialogFallDetect.value = it },
                totalTime = 10,
                expiredTime = {
                    showDialogFallDetect.value = false
                    //Send SMS
                    if (state.switchContact) {
                        showDialogSendSMS.value = true
                        viewModel.onEvent(MainEvent.SendSMS)
                    }

                }
            )
        }

        showDialogSendSMS.value -> {

            DialogSendSMS(
                setShowDialog = { showDialogSendSMS.value = it },
                isDone = isDoneSendSMS,
                clickableCancel = {
                    showDialogSendSMS.value = false
                })

        }

        showDialogPermissionSMS.value -> {

            DialogPermanentlyDenied(
                title = "Permission",
                message = "You need permission to access SMS to send SMS. Follow the steps below:",
                image = painterResource(id = R.drawable.img_sms_per),
                setShowDialog = {showDialogPermissionSMS.value = it},
                clickableDismiss = {showDialogPermissionSMS.value = false}
            )

        }
        showDialogPermissionLocation.value -> {

            DialogPermanentlyDenied(
                title = "Permission",
                message = "You need location permission. Follow the steps below:",
                image = painterResource(id = R.drawable.img_location),
                setShowDialog = {showDialogPermissionLocation.value = it},
                clickableDismiss = {showDialogPermissionLocation.value = false}
            )

        }
    }


}


@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {

    FalloutTheme {
    }
}