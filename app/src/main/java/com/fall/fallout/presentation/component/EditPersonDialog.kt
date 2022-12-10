package com.fall.fallout.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.fall.fallout.domain.model.Person
import com.fall.fallout.ui.theme.*

@Composable
fun EditPersonDialog(
    modifier: Modifier = Modifier,
    title: String = "Edit Person",
    setShowDialog: (Boolean) -> Unit,
    person: Person,
    onValueChangeFullName: (String) -> Unit,
    onValueChangePhoneNumber: (String) -> Unit,
    description: String = "Write the full name and phone number of your person to report falling.",
    clickableEdit: () -> Unit
) {

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

                Text(text = description, style = MaterialTheme.typography.body1)


                Spacer(modifier = Modifier.height(LARGE_PADDING))

                OutlineTextFieldSimple(
                    title = "Full name",
                    value = person.fullName,
                    onValueChange = onValueChangeFullName,
                    errorForLength = "Error",
                    isValid = {

                    },
                    isEnable = false,
                    minLength = 3
                )

                Spacer(modifier = Modifier.height(BETWEEN_PADDING))

                OutlineTextFieldSimple(
                    title = "Phone number",
                    value = person.phoneNumber,
                    onValueChange = onValueChangePhoneNumber,
                    errorForLength = "Error",
                    isValid = {

                    },
                    isEnable = false,
                    minLength = 11
                )

                Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
                Row {



                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(SIZE_HEIGHT_BUTTON),
                        onClick = {

                                  },
                        shape = RoundedCornerShape(CARD_ITEM_ROUNDED),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                    ) {
                        Text(text = "Confirm", color = Black)
                    }

                }


            }

        }

    }

}

@Preview
@Composable
fun EditPersonDialogPreview() {


    FalloutTheme {

       // EditPersonDialog(onValueChange = { v -> ali = v })
    }
}