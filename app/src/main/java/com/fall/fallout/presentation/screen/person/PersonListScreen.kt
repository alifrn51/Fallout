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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.fall.fallout.presentation.component.DialogMessage
import com.fall.fallout.presentation.component.ItemPerson
import com.fall.fallout.presentation.component.ToolbarApplication
import com.fall.fallout.presentation.screen.destinations.AddPersonScreenDestination
import com.fall.fallout.ui.theme.FalloutTheme
import com.fall.fallout.ui.theme.Gray500
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalPermissionsApi
@Destination
@Composable
fun PersonListScreen(
    navigator: DestinationsNavigator,
    viewModel: PersonViewModel = hiltViewModel()
) {


    val state = viewModel.state.value

    val showDialogDelete = remember { mutableStateOf(false) }

    var fullName by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }


    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(AddPersonScreenDestination)
                }, backgroundColor = Gray500
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "plusIcon",
                    tint = MaterialTheme.colors.primary
                )
            }
        },
        snackbarHost = {
            // reuse default SnackbarHost to have default animation and timing handling
            SnackbarHost(it) { data ->
                // custom snackbar with the custom colors
                Snackbar(
                    actionColor = MaterialTheme.colors.primary,
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onSurface,
                    snackbarData = data
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
                        image = person.image,
                        onClickDeleteItemPerson = {
                            showDialogDelete.value = true
                            viewModel.onEvent(PersonListEvent.CurrentPersonSelected(person))
                        },
                        onClickEditItemPerson = {

                        }
                    )
                }

            }
        }


        when {

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
                        scope.launch {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Contact deleted!",
                                actionLabel = "Restore"
                            )

                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.onEvent(PersonListEvent.RestorePerson)
                            }
                        }
                    },
                    setShowDialog = { showDialogDelete.value = it }
                )
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