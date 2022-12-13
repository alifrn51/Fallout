package com.fall.fallout.presentation.screen.setting

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.fall.fallout.domain.model.Setting
import com.fall.fallout.presentation.component.ItemSetting
import com.fall.fallout.presentation.component.ToolbarApplication
import com.fall.fallout.ui.theme.FalloutTheme
import com.fall.fallout.utils.serviceLocation.LocationService
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination()
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
) {

    val itemSettings = listOf(
        Setting(title = "Cancellation Time", icon = Icons.Outlined.Timer),
        //Setting(title = "Alarm", icon = Icons.Default.Alarm)
    )

    val context = LocalContext.current


    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState
    ) {


        Column(
            modifier = Modifier.fillMaxSize(),

            ) {
            ToolbarApplication(title = "Settings", modifier = Modifier.fillMaxWidth()){
                navigator.popBackStack()
            }


            LazyColumn {
                items(itemSettings) { setting ->


                    ItemSetting(icon = setting.icon, title = setting.title) {


                        Intent(context, LocationService::class.java).apply {
                            action = LocationService.ACTION_START
                        }

                    }

                }

            }
        }

    }


}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SettingsScreenPreview() {
    FalloutTheme {
        //SettingsScreen()
    }
}