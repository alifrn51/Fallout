package com.fall.fallout.presentation.screen.main

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.location.LocationManager
import android.os.Build
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fall.fallout.domain.model.LocationDetails
import com.fall.fallout.presentation.component.BoxSendingListActivation
import com.fall.fallout.presentation.component.ButtonBluer
import com.fall.fallout.presentation.screen.destinations.PersonListScreenDestination
import com.fall.fallout.presentation.screen.destinations.SettingsScreenDestination
import com.fall.fallout.ui.theme.BETWEEN_PADDING
import com.fall.fallout.ui.theme.FalloutTheme
import com.fall.fallout.ui.theme.SMALL_PADDING
import com.fall.fallout.utils.LocationLiveData
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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
        MapUiSettings(zoomControlsEnabled = false)
    }


    val context: Context = LocalContext.current

    val locationLiveData = LocationLiveData(context)


    val locationDetails = locationLiveData.value



    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = state.mapProperties,
            uiSettings = uiSettings,
            cameraPositionState = rememberCameraPositionState {
                locationDetails?.let {

                      position = CameraPosition.fromLatLngZoom(LatLng(it.lattitude.toDouble(), it.longitude.toDouble()), 10f)

                }

            }
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SMALL_PADDING)
                .align(Alignment.BottomCenter)
        ) {


            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {

                IconButton(onClick = {


                    locationLiveData.startLocationUpdates()

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