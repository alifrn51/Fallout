package com.fall.fallout.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fall.fallout.ui.theme.CARD_ITEM_ROUNDED
import com.fall.fallout.ui.theme.FalloutTheme
import com.fall.fallout.ui.theme.White

@Composable
fun OutlineTextFieldSimple(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    errorForLength: String,
    minLength: Int,
    isEnable: Boolean = false,
    isIconClearFiled: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isValid: (Boolean) -> Unit
) {

    var errorMessage by remember {
        mutableStateOf("")
    }

    errorMessage =
        if (value == "") ""
        else if (value.length < minLength)
            errorForLength
        else {
            ""
        }


    isValid(errorMessage.isEmpty() && value.isNotEmpty())


    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.primary
        )
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)

                errorMessage =
                    if (it == "") ""
                    else if (it.length < minLength)
                        errorForLength
                    else {
                        ""
                    }


            },
            textStyle = TextStyle(color = White),
            modifier = modifier
                .fillMaxWidth(),
            enabled = isEnable,
            label = { Text(text = label, style = MaterialTheme.typography.body1) },
            isError = errorMessage.isNotEmpty(),
            trailingIcon = {
                if (isIconClearFiled) {

                    AnimatedVisibility(visible = value.isNotEmpty()) {
                        IconButton(onClick = {
                            onValueChange("")
                            errorMessage = ""
                        }) {

                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear icon"
                            )

                        }
                    }


                }

            },
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            singleLine = true,

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.surface
            ),
            shape = RoundedCornerShape(CARD_ITEM_ROUNDED)
        )
        Text(
            text = errorMessage,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .padding(start = 8.dp)
            //.offset(y = (-0).dp)
        )
        /*AnimatedVisibility(visible = errorMessage.isNotEmpty()) {

        }*/


    }


}

@Preview
@Composable
fun TextFieldCPreview() {
    FalloutTheme {

    }
}