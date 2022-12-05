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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.fall.fallout.presentation.component.*
import com.fall.fallout.ui.theme.FalloutTheme
import com.fall.fallout.ui.theme.Gray500
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalPermissionsApi
@Destination
@Composable
fun PersonListScreen(
    navigator: DestinationsNavigator,
    viewModel: PersonListViewModel = hiltViewModel()
) {


    val state = viewModel.state.value

    val showDialogEdit = remember { mutableStateOf(false) }
    val showDialogDelete = remember { mutableStateOf(false) }
    val showDialogAdd = remember { mutableStateOf(false) }

    var fullName by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }


    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialogAdd.value = true }, backgroundColor = Gray500
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "plusIcon",
                    tint = MaterialTheme.colors.primary
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
                items(state.persons) { person ->

                    ItemPerson(
                        fullName = person.fullName,
                        phoneNumber = person.phoneNumber,
                        image = null,
                        onClickDeleteItemPerson = {
                            showDialogDelete.value = true
                            viewModel.onEvent(PersonListEvent.CurrentPersonSelected(person))
                        },
                        onClickEditItemPerson = {
                            showDialogEdit.value = true
                            viewModel.onEvent(PersonListEvent.CurrentPersonSelected(person))

                        }
                    )
                }

            }
        }

    }


    when {
        showDialogAdd.value -> {

            
            NewPersonDialog(
                valueTextFullName = fullName,
                valueTextPhoneNumber = phoneNumber,
                onValueChangeFullName = {fullName = it},
                onValueChangePhoneNumber = {phoneNumber = it},
                setShowDialog = { showDialogAdd.value = it },
                clickableAdd = {
                    viewModel.onEvent(PersonListEvent.AddPerson(fullName, phoneNumber))
                    showDialogAdd.value = false
                }
            )
            

        }

        showDialogEdit.value -> {

            /*EditPersonDialog(
                setShowDialog = { showDialogEdit.value = it },
                onValueChangeFullName = { fullNameValueChanged = it },
                onValueChangePhoneNumber = { phoneNumberValueChanged = it },
                person = state.personSelected ?: return,
                clickableEdit = {
                    viewModel.onEvent(PersonListEvent.EditPerson)
                    showDialogDelete.value = false

                }
            )*/


        }

        showDialogDelete.value -> {

            DialogMessage(
                title = "Delete Person",
                message = "Are your sure to delete this person from list?",
                clickableCancel = {
                    showDialogDelete.value = false
                },
                clickableDelete = {
                    viewModel.onEvent(PersonListEvent.DeletePerson)
                    showDialogDelete.value = false
                },
                setShowDialog = {showDialogDelete.value = it}
            )
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