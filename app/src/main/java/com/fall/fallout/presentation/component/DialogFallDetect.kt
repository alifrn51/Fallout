package com.fall.fallout.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fall.fallout.ui.theme.*
import java.lang.StringBuilder

@Composable
fun DialogFallDetect(
    title: String,
    clickableIamOk: () -> Unit={},
) {

    Card(
        shape = RoundedCornerShape(CARD_ITEM_ROUNDED)
    ) {


        Column(modifier = Modifier.padding(LARGE_PADDING)) {

            Text(text = title , style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            Text(text = buildAnnotatedString {
                append("If you are ok press ")
                withStyle(style = SpanStyle(color = MaterialTheme.colors.secondary)){
                    append("I'm Ok")
                }
                append(" Button.")
            }, style = MaterialTheme.typography.body1)

            Spacer(modifier = Modifier.height(MEDIUM_PADDING))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "00", style = MaterialTheme.typography.h3, color = MaterialTheme.colors.secondary)
                Text(text = ":", style = MaterialTheme.typography.h3, color = MaterialTheme.colors.secondary)
                Text(text = "10", style = MaterialTheme.typography.h3, color = MaterialTheme.colors.secondary)
            }

            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))

            Row {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(SIZE_HEIGHT_BUTTON),
                    shape = RoundedCornerShape(CARD_ITEM_ROUNDED), onClick = { clickableIamOk() }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)) {

                    Text(text = "I'm Ok" , color = Black)

                }


            }


        }
    }

}

@Preview
@Composable
fun DialogFallDetectPreview() {
    FalloutTheme {
        DialogFallDetect(
            title = "Fall Detected"
        )
    }
}