package com.fall.fallout.presentation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fall.fallout.ui.theme.Black
import com.fall.fallout.ui.theme.FalloutTheme
import com.fall.fallout.ui.theme.White

@Composable
fun CustomSwitch(
    scale: Float = 2f,
    width: Dp = 36.dp,
    height: Dp = 20.dp,
    strokeWidth: Dp = 2.dp,
    cornerRadius:Dp = 100.dp,
    checkedTrackColor: Color = Color.White,
    uncheckedTrackColor: Color = Black,
    checkedBackgroundSwitch: Color = MaterialTheme.colors.primary,
    uncheckedBackgroundSwitch: Color = White,
    gapBetweenThumbAndTrackEdge: Dp = 6.dp,
    switchON: MutableState<Boolean>
) {


    val thumbRadius = (height / 2) - gapBetweenThumbAndTrackEdge

    // To move thumb, we need to calculate the position (along x axis)
    val animatePosition = animateFloatAsState(
        targetValue = if (switchON.value)
            with(LocalDensity.current) { (width - thumbRadius - gapBetweenThumbAndTrackEdge).toPx() }
        else
            with(LocalDensity.current) { (thumbRadius + gapBetweenThumbAndTrackEdge).toPx() }
    )

    Canvas(
        modifier = Modifier
            .size(width = width, height = height)
            .scale(scale = scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        // This is called when the user taps on the canvas
                        switchON.value = !switchON.value
                    }
                )
            }
    ) {

        drawRoundRect(
            color = if (switchON.value) checkedBackgroundSwitch else uncheckedBackgroundSwitch,
            cornerRadius = CornerRadius(x = cornerRadius.toPx(), y = cornerRadius.toPx()),
        )
        // Track
        drawRoundRect(
            color = if (switchON.value) checkedTrackColor else uncheckedTrackColor,
            cornerRadius = CornerRadius(x = cornerRadius.toPx(), y = cornerRadius.toPx()),
            style =  Stroke(width = strokeWidth.toPx())
        )

        // Thumb
        drawCircle(
            color = if (switchON.value) checkedTrackColor else uncheckedTrackColor,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = animatePosition.value,
                y = size.height / 2
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun CustomSwitchPreview() {
    FalloutTheme {
        //CustomSwitch()
    }
}