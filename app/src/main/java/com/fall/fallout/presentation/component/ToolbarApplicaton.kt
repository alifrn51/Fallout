package com.fall.fallout.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.fall.fallout.ui.theme.FalloutTheme
import com.fall.fallout.ui.theme.LARGE_PADDING
import com.fall.fallout.ui.theme.SMALL_PADDING

@Composable
fun ToolbarApplication(
    modifier: Modifier = Modifier,
    title: String
) {



    Box(
        modifier = modifier
            .padding(vertical = LARGE_PADDING, horizontal = SMALL_PADDING)
    ) {
        
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "ArrowBack",
            tint = MaterialTheme.colors.secondary
        )
        
        Text(text = title, style = MaterialTheme.typography.h6, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

    }
    
}

@Preview
@Composable
fun ToolbarApplicationPreview() {
    FalloutTheme {
        ToolbarApplication(title = "Person List")
    }
}