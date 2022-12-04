package com.fall.fallout.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fall.fallout.ui.theme.CARD_ITEM_ROUNDED
import com.fall.fallout.ui.theme.FalloutTheme
import com.fall.fallout.ui.theme.White
import com.fall.fallout.ui.theme.Yellow700

@Composable
fun OutlineTextFieldSimple(
    title:String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    errorForLength : String,
    minLength:Int = 2,
    isIconClearFiled: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isValid: (Boolean) -> Unit = {}
) {

    var errorMessage by remember {
        mutableStateOf("")
    }

    isValid(errorMessage.isEmpty() && value.isNotEmpty())





    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Text(text = title.toUpperCase() , style = MaterialTheme.typography.subtitle2 , color = MaterialTheme.colors.secondary)
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)

                errorMessage =
                    if (it == "") ""
                    else if (value.length<minLength)
                        "$errorForLength $minLength"
                    else {
                        ""
                    }


            },
            textStyle = TextStyle(color = White),
            modifier = modifier
                .fillMaxWidth(),
            label = { Text(text = label, style = MaterialTheme.typography.body1 ) },
            isError = if (errorMessage == "") false else errorMessage.isNotEmpty(),
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
                focusedBorderColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor =  MaterialTheme.colors.secondary),
            shape = RoundedCornerShape(CARD_ITEM_ROUNDED)
        )
        Text(
            text = errorMessage,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
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

        var ali = "ali"
        OutlineTextFieldSimple(value = "ALi", onValueChange = {value-> ali = value} , errorForLength = "HI", title = "Full name")
    }
}