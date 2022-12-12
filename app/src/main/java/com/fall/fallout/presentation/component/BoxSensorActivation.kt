package com.fall.fallout.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fall.fallout.domain.model.Person
import com.fall.fallout.ui.theme.CARD_ITEM_ROUNDED
import com.fall.fallout.ui.theme.SMALL_PADDING

@Composable
fun BoxSensorActivation(
    modifier: Modifier = Modifier,
    switchOnChange: (Boolean) -> Unit
) {
    val switch = remember {
            mutableStateOf(false)
        }



    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(CARD_ITEM_ROUNDED)
    ) {


        Row(
            modifier = Modifier.padding(SMALL_PADDING)
        ) {

            Text(modifier = Modifier.weight(1f), text = "Sensor Activation", style = MaterialTheme.typography.h6)

            CustomSwitch(
                scale = 1f,
                width = 52.dp,
                height = 30.dp,
                switchON = switch,
                switchOnChange = switchOnChange
            )
        }
    }

}

