package com.fall.fallout.presentation.screen.main

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.fall.fallout.R
import com.fall.fallout.domain.model.Person
import com.fall.fallout.presentation.component.BoxSendingListActivation
import com.fall.fallout.presentation.component.ButtonBluer
import com.fall.fallout.ui.theme.*

@Composable
fun MainScreen(

) {

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
                )
                Spacer(modifier = Modifier.width(BETWEEN_PADDING))
                ButtonBluer(
                    modifier = Modifier.weight(0.5f),
                    icon = Icons.Default.Settings,
                    title = "Settings"
                )

            }

            Spacer(modifier = Modifier.height(BETWEEN_PADDING))

            BoxSendingListActivation(listPerson = listPerson)
        }

    }


}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {

    FalloutTheme {
        MainScreen()
    }
}