package com.fall.fallout.presentation.component

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.fall.fallout.ui.theme.*
import com.fall.fallout.R
import com.fall.fallout.utils.isPermanentlyDenied
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
@SuppressLint("Recycle", "IntentReset")
@Composable
fun NewPersonDialog(
    modifier: Modifier = Modifier,
    title: String = "New Person",
    setShowDialog: (Boolean) -> Unit,
    clickableAdd: () -> Unit,
    valueTextFullName: String,
    valueTextPhoneNumber: String,
    onValueChangeFullName: (String) -> Unit,
    onValueChangePhoneNumber: (String) -> Unit,
    description: String = "Write the full name and phone number of your person to report falling."
) {

    var isValidFullName by remember {
        mutableStateOf(false)
    }
    var isValidPhoneNumber by remember {
        mutableStateOf(false)
    }


    val result = remember { mutableStateOf<Intent?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result.value = it.data
        }

    val permissionState = rememberPermissionState(permission = Manifest.permission.READ_CONTACTS)


    Dialog(onDismissRequest = { setShowDialog(false) }) {

        Card(
            shape = RoundedCornerShape(CARD_DIALOG_ROUNDED)
        ) {

            Column(
                modifier
                    .padding(LARGE_PADDING)
            ) {

                Text(text = title, style = MaterialTheme.typography.h6)

                Spacer(modifier = Modifier.height(BETWEEN_PADDING))

                Text(text = description, style = MaterialTheme.typography.body2)


                Spacer(modifier = Modifier.height(LARGE_PADDING))



                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Icon( modifier = Modifier
                        .size(104.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(250.dp)
                        )
                        .padding(6.dp)
                        .clip(shape = RoundedCornerShape(250.dp))
                        .background(Gray500)
                        .clickable {

                        }
                        .padding(12.dp),
                        tint = Gray200,
                        imageVector = Icons.Default.Person,
                        contentDescription ="Image Person"
                    )
                }

                Spacer(modifier = Modifier.height(LARGE_PADDING))

                OutlineTextFieldSimple(
                    title = "Full name",
                    value = valueTextFullName,
                    onValueChange = onValueChangeFullName,
                    errorForLength = "Error",
                    isValid = {
                        isValidFullName = it
                    },
                    isEnable = false
                )

                Spacer(modifier = Modifier.height(BETWEEN_PADDING))

                OutlineTextFieldSimple(
                    title = "Phone number",
                    value = valueTextPhoneNumber,
                    onValueChange = onValueChangePhoneNumber,
                    errorForLength = "Error",
                    isValid = {
                        isValidPhoneNumber = it
                    },
                    isEnable = false
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

                                    launcher.launch(pickContact)
                                }
                                permissionState.shouldShowRationale -> {

                                }

                                permissionState.isPermanentlyDenied() -> {

                                }
                            }
                        },
                        shape = RoundedCornerShape(CARD_ITEM_ROUNDED),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Gray200)
                    ) {


                        Icon(
                            imageVector = Icons.Default.Contacts,
                            contentDescription = "Contact",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.size(16.dp)
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
                        onClick = { if (isValidFullName && isValidPhoneNumber) clickableAdd() },
                        shape = RoundedCornerShape(CARD_ITEM_ROUNDED),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    ) {
                        Text(text = "Confirm", color = Black)
                    }

                }


            }

        }

    }


    result.value?.data?.let { uri ->


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
            onValueChangeFullName(name)
            onValueChangePhoneNumber(number)


        }
    }


}


@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
fun NewPersonDialogPreview() {

    val showDialogAdd = remember { mutableStateOf(true) }

    var fullName by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }

    FalloutTheme {

        NewPersonDialog(
            setShowDialog = {showDialogAdd.value = it},
            clickableAdd = { /*TODO*/ },
            valueTextFullName = fullName,
            valueTextPhoneNumber = phoneNumber,
            onValueChangeFullName = {fullName = it},
            onValueChangePhoneNumber = {phoneNumber = it}
        )

    }
}