package com.fall.fallout.presentation.screen.person

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.fall.fallout.R
import com.fall.fallout.domain.model.Person
import com.fall.fallout.presentation.component.EditPersonDialog
import com.fall.fallout.presentation.component.ItemPerson
import com.fall.fallout.presentation.component.ToolbarApplication
import com.fall.fallout.ui.theme.FalloutTheme
import com.fall.fallout.ui.theme.Gray500
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination()
@Composable
fun PersonListScreen(
    navigator: DestinationsNavigator,
) {


    var fullNameValueChanged by remember {
        mutableStateOf("")
    }
    val phoneNumberValueChanged by remember {
        mutableStateOf("")
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



    val showDialog =  remember { mutableStateOf(false) }


    if(showDialog.value)
        EditPersonDialog(
            setShowDialog = { showDialog.value = it},
            onValueChangeFullName ={},
            onValueChangePhoneNumber ={}
        )

    var scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }, backgroundColor = Gray500) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "plusIcon",
                    tint = MaterialTheme.colors.secondary
                )
            }
        }
    ) {


        Column(
            modifier = Modifier.fillMaxSize(),

            ) {
            ToolbarApplication(title = "Person List", modifier = Modifier.fillMaxWidth()) {
                navigator.popBackStack()
            }


            LazyColumn {
                items(listPerson) { person ->

                    ItemPerson(
                        fullName = "${person.firstName} ${person.lastName}",
                        phoneNumber = person.phoneNumber,
                        image = person.image,
                        onClickDeleteItemPerson = {



                        },
                        onClickEditItemPerson = {
                            showDialog.value = true
                        }
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
        //PersonListScreen()
    }
}