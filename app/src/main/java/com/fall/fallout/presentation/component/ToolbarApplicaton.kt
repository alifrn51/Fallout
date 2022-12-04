package com.fall.fallout.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fall.fallout.ui.theme.FalloutTheme
import com.fall.fallout.ui.theme.LARGE_PADDING
import com.fall.fallout.ui.theme.SMALL_PADDING

@Composable
fun ToolbarApplication(
    modifier: Modifier = Modifier,
    title: String,
    clickable: () -> Unit
) {


    Box(

        modifier = modifier.fillMaxWidth().height(80.dp)
            .padding(vertical = LARGE_PADDING, horizontal = SMALL_PADDING),
    ) {
        IconButton(onClick = { clickable() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "ArrowBack",
                tint = MaterialTheme.colors.secondary
            )
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

        }
    }


}

@Preview
@Composable
fun ToolbarApplicationPreview() {
    FalloutTheme {
        ToolbarApplication(title = "Person List") {

        }
    }
}