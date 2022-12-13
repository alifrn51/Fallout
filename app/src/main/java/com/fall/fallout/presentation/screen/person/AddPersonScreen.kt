package com.fall.fallout.presentation.screen.person

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.RestoreFromTrash
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.fall.fallout.R
import com.fall.fallout.presentation.component.ModalChoosePic
import com.fall.fallout.presentation.component.OutlineTextFieldSimple
import com.fall.fallout.presentation.component.ToolbarApplication
import com.fall.fallout.ui.theme.*
import com.fall.fallout.utils.ComposeFileProvider
import com.fall.fallout.utils.UiEvent
import com.fall.fallout.utils.isPermanentlyDenied
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@SuppressLint("IntentReset", "Recycle")
@ExperimentalComposeUiApi
@ExperimentalPermissionsApi
@Destination
@Composable
fun AddPersonScreen(
    navigator: DestinationsNavigator,
    viewModel: PersonViewModel = hiltViewModel()
) {

    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var isValidFullName by remember { mutableStateOf(false) }
    var isValidPhoneNumber by remember { mutableStateOf(false) }


    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }


    val resultContact = remember { mutableStateOf<Intent?>(null) }
    val launcherContact =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            resultContact.value = it.data
        }

    val permissionState = rememberPermissionState(permission = Manifest.permission.READ_CONTACTS)



    val personViewModel = resultContact.value.apply {

    }


    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            imageUri = it.data?.data
        }
    )



    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            imageUri = uri
        }
    )


    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val scaffoldState = rememberScaffoldState()

    //val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is UiEvent.AddPerson -> {
                    navigator.navigateUp()
                }
            }
        }
    }

    val interactionSource = remember { MutableInteractionSource() }




    ModalChoosePic(
        onClickGallery = {

            imagePicker.launch("image/*")

        },
        onClickCamera = {



           // cameraLauncher.launch(Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE))



        }
    ) { state, scope ->

        //Make ModalBottomSheetLayout to wrap the content

        Scaffold(
            scaffoldState = scaffoldState,
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
            },
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) {

                focusManager.clearFocus()
                keyboardController?.hide()

            },
        ) {

            Column {
                ToolbarApplication(
                    title = "New Person",
                    icon = Icons.Default.Close
                ) {
                    navigator.popBackStack()
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(LARGE_PADDING)
                ) {


                    Text(
                        text = "Write the full name and phone number of your person to report falling.",
                        style = MaterialTheme.typography.body1
                    )


                    Spacer(modifier = Modifier.height(LARGE_PADDING))





                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box {

                            Image(modifier = Modifier
                                .size(160.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colors.primary,
                                    shape = RoundedCornerShape(250.dp)
                                )
                                .padding(4.dp)
                                .clip(shape = RoundedCornerShape(250.dp))
                                .background(Gray500)
                                .clickable {


                                    focusManager.clearFocus()
                                    keyboardController?.hide()


                                    scope.launch {
                                        state.animateTo(
                                            ModalBottomSheetValue.Expanded,
                                            tween(500)
                                        )
                                    }


                                },
                                painter = if (imageUri != null) rememberAsyncImagePainter(
                                    model = imageUri,
                                    contentScale = ContentScale.Crop
                                )else painterResource(id = R.drawable.ic_person),
                                contentScale = ContentScale.Crop,
                                contentDescription = "Image Person"
                            )


                            Icon(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(42.dp)
                                    .clip(shape = RoundedCornerShape(250.dp))
                                    .background(Gray200)
                                    .clickable {
                                        if (imageUri != null)
                                            imageUri = null
                                        else {

                                            focusManager.clearFocus()
                                            keyboardController?.hide()


                                            scope.launch {
                                                state.animateTo(
                                                    ModalBottomSheetValue.Expanded,
                                                    tween(500)
                                                )
                                            }


                                        }
                                    }
                                    .padding(8.dp)
                                    ,
                                tint = MaterialTheme.colors.primary,
                                imageVector = if (imageUri==null)
                                    Icons.Default.Add
                                else
                                    Icons.Outlined.DeleteOutline,
                                contentDescription = "Add Photo",
                            )
                        }

                    }

                    Spacer(modifier = Modifier.height(LARGE_PADDING))

                    OutlineTextFieldSimple(
                        title = "Full name",
                        value = fullName,
                        onValueChange = { fullName = it },
                        errorForLength = "The full name should not be less than 3 characters",
                        isValid = {
                            isValidFullName = it
                        },
                        minLength = 3,
                        isIconClearFiled = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        isEnable = true
                    )

                    Spacer(modifier = Modifier.height(BETWEEN_PADDING))

                    OutlineTextFieldSimple(
                        title = "Phone number",
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        errorForLength = "The phone number format is not valid!\nex:09*********",
                        isValid = {
                            isValidPhoneNumber = it
                        },
                        minLength = 11,
                        isIconClearFiled = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            // cancel focus and hide keyboard

                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }),
                        isEnable = true,

                        )

                    Spacer(modifier = Modifier.height(LARGE_PADDING))
                    Row {

                        Button(
                            modifier = Modifier
                                .weight(0.6f)
                                .height(SIZE_HEIGHT_BUTTON),
                            onClick = {


                                permissionState.launchPermissionRequest()
                                when {
                                    permissionState.hasPermission -> {


                                        val pickContact = Intent(
                                            Intent.ACTION_PICK,
                                            ContactsContract.Contacts.CONTENT_URI
                                        )
                                        pickContact.type =
                                            ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE

                                        launcherContact.launch(pickContact)
                                    }
                                    permissionState.shouldShowRationale -> {


                                        scope.launch {

                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Need Contact permission, please allow access from phone settings.",
                                            )

                                        }

                                    }

                                    permissionState.isPermanentlyDenied() -> {

                                    }
                                }
                            },
                            shape = RoundedCornerShape(CARD_ITEM_ROUNDED),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Gray500)
                        ) {


                            Icon(
                                imageVector = Icons.Default.Contacts,
                                contentDescription = "Contact",
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))

                            Text(text = "Load Contact")
                        }

                        Spacer(modifier = Modifier.width(BETWEEN_PADDING))

                        Button(
                            modifier = Modifier
                                .weight(0.4f)
                                .height(SIZE_HEIGHT_BUTTON)
                                .alpha(if (isValidFullName && isValidPhoneNumber) 1f else 0.5f),
                            onClick = {
                                if (isValidFullName && isValidPhoneNumber) {

                                    viewModel.onEvent(
                                        PersonListEvent.AddPerson(
                                            fullName,
                                            phoneNumber,
                                            image = if (imageUri != null) imageUri.toString() else null
                                        )
                                    )
                                }
                            },
                            shape = RoundedCornerShape(CARD_ITEM_ROUNDED),
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                        ) {
                            Text(text = "Confirm", color = Black)
                        }

                    }


                }

            }

        }


        BackHandler(
            enabled = (state.currentValue == ModalBottomSheetValue.HalfExpanded ||
                    state.currentValue == ModalBottomSheetValue.Expanded),
            onBack = {
                scope.launch {
                    state.animateTo(ModalBottomSheetValue.Hidden, tween(300))
                }
            }
        )


    }


    resultContact.value?.data?.let { uri ->


        // on below line we are creating a cursor
        val cursor: Cursor? =
            LocalContext.current.contentResolver.query(uri, null, null, null, null)

        cursor?.let {

            // on below line we are moving cursor.
            cursor.moveToFirst()

            // on below line we are getting our
            // number and name from cursor
            val number: String =
                cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val name: String =
                cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

            // on the below line we are setting values.
            fullName = name
            phoneNumber = number

            focusManager.clearFocus()
            keyboardController?.hide()

        }
    }

}