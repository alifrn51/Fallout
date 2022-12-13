package com.fall.fallout.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.fall.fallout.R
import com.fall.fallout.ui.theme.*

@Composable
fun DialogPermanentlyDenied(
    title: String,
    message: String,
    image: Painter,
    setShowDialog: (Boolean) -> Unit,
    clickableDismiss: () -> Unit,
) {

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Card(
            shape = RoundedCornerShape(CARD_ITEM_ROUNDED)
        ) {


            Column(modifier = Modifier.padding(LARGE_PADDING)) {

                Text(text = title, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier.size(120.dp),
                        painter = image,
                        contentDescription = "SMS"
                    )
                }


                Spacer(modifier = Modifier.height(MEDIUM_PADDING))

                Text(text = message, style = MaterialTheme.typography.body1)

                Text(modifier = Modifier.padding(top = 10.dp), text = "Setting/Apps/Fallout/App permissions", style = MaterialTheme.typography.body1)

                Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))

                Row {

                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(SIZE_HEIGHT_BUTTON),
                        shape = RoundedCornerShape(CARD_ITEM_ROUNDED),
                        elevation = ButtonDefaults.elevation(0.dp),
                        border = BorderStroke(1.dp, color = Gray200),
                        colors = ButtonDefaults.buttonColors(backgroundColor =  MaterialTheme.colors.surface),
                        onClick = { clickableDismiss() },

                        ) {

                        Text(text = "Dismiss", color = White)

                    }


                }


            }
        }

    }

}

