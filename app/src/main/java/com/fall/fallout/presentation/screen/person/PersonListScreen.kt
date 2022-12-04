package com.fall.fallout.presentation.screen.person

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fall.fallout.R
import com.fall.fallout.domain.model.Person
import com.fall.fallout.presentation.component.ItemPerson
import com.fall.fallout.presentation.component.ToolbarApplication
import com.fall.fallout.ui.theme.BETWEEN_PADDING
import com.fall.fallout.ui.theme.FalloutTheme
import com.fall.fallout.ui.theme.Gray500
import com.fall.fallout.ui.theme.SMALL_PADDING

@Composable
fun PersonListScreen() {

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
            firstName = "Hasan",
            lastName = "Shadi",
            phoneNumber = "09154325684",
            image = painterResource(
                id = R.drawable.sample
            )
        ),
        Person(
            firstName = "Hamed",
            lastName = "Ahangi",
            phoneNumber = "09121514134",
            image = painterResource(
                id = R.drawable.sample
            )
        ),
    )


    var scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }, backgroundColor = Gray500) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "plusIcon", tint = MaterialTheme.colors.secondary)
            }
        }
    ) {


        Column(
            modifier = Modifier.fillMaxSize(),

            ) {
            ToolbarApplication(title = "Person List", modifier = Modifier.fillMaxWidth())


            LazyColumn {
                items(listPerson) { person ->

                    ItemPerson(
                        fullName = "${person.firstName} ${person.lastName}",
                        phoneNumber = person.phoneNumber,
                        image = person.image
                    )
                }

            }
        }

    }


}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PersonListScreenPreview() {
    FalloutTheme {
        PersonListScreen()
    }
}