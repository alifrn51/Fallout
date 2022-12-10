package com.fall.fallout.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fall.fallout.ui.theme.*
import androidx.compose.ui.text.TextStyle as TextStyle1

@Composable
fun ButtonBluer(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    clickable: () -> Unit
) {


    Card(
        modifier = modifier
            .height(SIZE_HEIGHT_BUTTON)
            .clickable { clickable() },
        shape = RoundedCornerShape(CARD_ITEM_ROUNDED),
        elevation = 2.dp
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = "Icon ${title.substring(0..2)}",
                tint = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.width(BETWEEN_PADDING))

            Text(text = title, color = White, style = MaterialTheme.typography.button)
        }


    }


}

@Composable
fun PersonList() {
    Button(
        onClick = { },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffeef0f2).copy(alpha = 0.2f)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                end = 23.dp,
                top = 10.dp,
                bottom = 10.dp
            )
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Icon/Person",
            tint = Color(0xffeec643),
            modifier = Modifier
                .height(height = 15.dp)
        )
        Spacer(
            modifier = Modifier
                .width(width = 8.dp)
        )
        Text(
            text = "Person List",
            color = Color(0xffeef0f2),
            style = TextStyle1(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Preview
@Composable
fun ButtonBluerPreview() {

    FalloutTheme {

        ButtonBluer(icon = Icons.Default.Person, title = "Person List") {}
    }
}