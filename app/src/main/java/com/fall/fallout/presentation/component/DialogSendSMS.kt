package com.fall.fallout.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fall.fallout.R
import com.fall.fallout.ui.theme.*

@Composable
fun DialogSendSMS(
    title: String = "Sending SMS",
    setShowDialog: (Boolean) -> Unit,
    clickableCancel: () -> Unit= {},
    isDone: MutableState<Boolean>
) {

    Dialog(onDismissRequest = { setShowDialog(false) },properties = DialogProperties(dismissOnBackPress = false , dismissOnClickOutside = false)) {
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
                        painter = painterResource(id = R.drawable.img_sms),
                        contentDescription = "SMS"
                    )
                }

                if (isDone.value) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(MEDIUM_PADDING),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary,
                        text = "Done",
                        style = MaterialTheme.typography.h6
                    )

                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        LinearProgressIndicator()
                    }
                }

                Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))

                Row {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(SIZE_HEIGHT_BUTTON),
                        shape = RoundedCornerShape(CARD_ITEM_ROUNDED),
                        elevation = ButtonDefaults.elevation(0.dp),
                        border = BorderStroke(1.dp, color = Gray200),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                        onClick = { clickableCancel() }

                    ) {

                        Text(text = "dismiss", color = White)

                    }


                }


            }
        }


    }
}
