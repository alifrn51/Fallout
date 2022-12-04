package com.fall.fallout.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fall.fallout.ui.theme.*

@Composable
fun DialogMessage(
    title: String,
    message: String,
    clickableDelete: () -> Unit={},
    clickableCancel: () -> Unit={},
) {

    Card(
        shape = RoundedCornerShape(CARD_ITEM_ROUNDED)
    ) {


        Column(modifier = Modifier.padding(LARGE_PADDING)) {

            Text(text = title , style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            Text(text = message, style = MaterialTheme.typography.body1)

            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))

            Row {
                Button(modifier = Modifier
                    .weight(0.5f)
                    .height(SIZE_HEIGHT_BUTTON), onClick = { clickableCancel() },
                    shape = RoundedCornerShape(CARD_ITEM_ROUNDED), elevation = ButtonDefaults.elevation(0.dp), border = BorderStroke(1.dp , color = Gray200),colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {

                    Text(text = "Cancel" , color = White)

                }
                Spacer(modifier = Modifier.width(BETWEEN_PADDING))
                Button(modifier = Modifier
                    .weight(0.5f)
                    .height(SIZE_HEIGHT_BUTTON),
                    shape = RoundedCornerShape(CARD_ITEM_ROUNDED), onClick = { clickableDelete() }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)) {

                    Text(text = "Delete" , color = Black)

                }


            }


        }
    }

}

@Preview
@Composable
fun DialogMessagePreview() {
    FalloutTheme {
        DialogMessage(
            title = "Delete Person",
            message = "Are your sure to delete this person from list?"
        )
    }
}