package com.fall.fallout.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fall.fallout.ui.theme.BETWEEN_PADDING
import com.fall.fallout.ui.theme.CARD_ITEM_ROUNDED
import com.fall.fallout.ui.theme.FalloutTheme
import com.fall.fallout.ui.theme.SMALL_PADDING

@Composable
fun ItemSetting(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    clickable: () -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = SMALL_PADDING, end = SMALL_PADDING, bottom = BETWEEN_PADDING)
            .clickable {
                clickable()
            },

        shape = RoundedCornerShape(CARD_ITEM_ROUNDED)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(SMALL_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(imageVector = icon, contentDescription = "icon $title" , tint = MaterialTheme.colors.secondary)

            Spacer(modifier = Modifier.width(BETWEEN_PADDING))

            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = MaterialTheme.typography.body1
            )

        }
    }

}

@Preview
@Composable
fun ItemSettingPreview() {
    FalloutTheme {
        ItemSetting(icon =Icons.Outlined.Timer, title ="Cancellation Time"){

        }
    }
}