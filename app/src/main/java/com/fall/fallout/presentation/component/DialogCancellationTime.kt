package com.fall.fallout.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fall.fallout.ui.theme.*

@Composable
fun DialogCancellationTime(
    title: String,
    description: String,
    clickableSave: () -> Unit = {},
) {

    Card(
        shape = RoundedCornerShape(CARD_ITEM_ROUNDED)
    ) {


        Column(modifier = Modifier.padding(LARGE_PADDING)) {

            Text(text = title, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            Text(text = description, style = MaterialTheme.typography.body1)

            Spacer(modifier = Modifier.height(LARGE_PADDING))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {


                Text(
                    modifier = Modifier
                        .border(
                            1.dp,
                            color = MaterialTheme.colors.secondary,
                            shape = RoundedCornerShape(CARD_ITEM_ROUNDED)
                        )
                        .padding(horizontal = 8.dp)
                        .clickable { },
                    text = "00",
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.secondary
                )
                Text(
                    text = " : ",
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.secondary
                )
                Text(
                    modifier = Modifier
                        .border(
                            1.dp,
                            color = MaterialTheme.colors.secondary,
                            shape = RoundedCornerShape(CARD_ITEM_ROUNDED)
                        )
                        .padding(horizontal = 8.dp)
                        .clickable { },
                    text = "59",
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.secondary
                )
            }

            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))

            Row {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(SIZE_HEIGHT_BUTTON),
                    shape = RoundedCornerShape(CARD_ITEM_ROUNDED),
                    onClick = { clickableSave() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
                ) {

                    Text(text = "Save", color = Black)

                }


            }


        }
    }

}

@Preview
@Composable
fun DialogCancellationTimePreview() {
    FalloutTheme {
        DialogCancellationTime(
            title = "Detect Cancellation Time",
            description = "Choose duration for cancelling detect."
        )
    }
}