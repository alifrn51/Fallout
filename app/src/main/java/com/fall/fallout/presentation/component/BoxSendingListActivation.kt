package com.fall.fallout.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fall.fallout.R
import com.fall.fallout.domain.model.Person
import com.fall.fallout.ui.theme.*

@Composable
fun BoxSendingListActivation(
    modifier: Modifier = Modifier,
    listPerson: List<Person>,
    switchON: MutableState<Boolean>
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(CARD_ITEM_ROUNDED)
    ) {

        Column(
            modifier = Modifier.padding(SMALL_PADDING)
        ) {

            Row {

                Text(modifier = Modifier.weight(1f), text = "Sending List Activation", style = MaterialTheme.typography.h6)

                CustomSwitch(
                    scale = 1f,
                    width = 52.dp,
                    height = 30.dp,
                    switchON = switchON
                )
            }

            Divider(
                color = Gray200,
                modifier = Modifier.padding(vertical = BETWEEN_PADDING)
            )

            LazyRow {
                items(listPerson) { person ->

                    Column(
                        modifier = Modifier
                            .padding(end = BETWEEN_PADDING),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(shape = RoundedCornerShape(100.dp)),
                            painter = person.image,
                            contentDescription = "person",
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            modifier = Modifier.width(40.dp),
                            text = "${person.firstName} ${person.lastName}",
                            style = MaterialTheme.typography.overline,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            color = Color.LightGray,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(32.dp))

        }

    }
}

@Preview
@Composable
fun BoxSendingListActivationPreview() {

    /*FalloutTheme {
        BoxSendingListActivation(
            listPerson = listOf(
                Person(
                    firstName = "Ali",
                    lastName = "Frn",
                    phoneNumber = "09155524447",
                    image = painterResource(
                        id = R.drawable.sample
                    )
                ),
                Person(
                    firstName = "Ali",
                    lastName = "Frnfdf",
                    phoneNumber = "09155524447",
                    image = painterResource(
                        id = R.drawable.sample
                    )
                ),
                Person(
                    firstName = "Ali",
                    lastName = "Frn",
                    phoneNumber = "09155524447",
                    image = painterResource(
                        id = R.drawable.sample
                    )
                ),
            )
        )
    }*/
}