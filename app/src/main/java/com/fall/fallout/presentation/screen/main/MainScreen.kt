package com.fall.fallout.presentation.screen.main

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.fall.fallout.R
import com.fall.fallout.domain.model.Person
import com.fall.fallout.presentation.component.BoxSendingListActivation
import com.fall.fallout.presentation.component.ButtonBluer
import com.fall.fallout.presentation.screen.destinations.PersonListScreenDestination
import com.fall.fallout.presentation.screen.destinations.SettingsScreenDestination
import com.fall.fallout.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination()
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
) {

    var switchON = remember {
        mutableStateOf(false)
    }
    var listPerson = listOf(
        Person(
            firstName = "Ali",
            lastName = "Frn",
            phoneNumber = "09155524447",
            image = painterResource(
                id = R.drawable.sample
            )
        ),
        Person(
            firstName = "Ali",
            lastName = "Frn",
            phoneNumber = "09155524447",
            image = painterResource(
                id = R.drawable.sample
            )
        ),
        Person(
            firstName = "Ali",
            lastName = "Frn",
            phoneNumber = "09155524447",
            image = painterResource(
                id = R.drawable.sample
            )
        ),
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SMALL_PADDING)
                .align(Alignment.BottomCenter)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {

                ButtonBluer(
                    modifier = Modifier.weight(0.5f),
                    icon = Icons.Default.Person,
                    title = "Person List"
                ){
                    navigator.navigate(PersonListScreenDestination)

                }
                Spacer(modifier = Modifier.width(BETWEEN_PADDING))
                ButtonBluer(
                    modifier = Modifier.weight(0.5f),
                    icon = Icons.Default.Settings,
                    title = "Settings"
                ){
                    navigator.navigate(SettingsScreenDestination)
                }

            }

            Spacer(modifier = Modifier.height(BETWEEN_PADDING))

            BoxSendingListActivation(listPerson = listPerson, switchON = switchON)
        }

    }


}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {

    FalloutTheme {
    }
}