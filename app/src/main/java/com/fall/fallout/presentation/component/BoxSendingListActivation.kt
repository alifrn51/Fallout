package com.fall.fallout.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.fall.fallout.R
import com.fall.fallout.domain.model.Person
import com.fall.fallout.ui.theme.*

@Composable
fun BoxSendingListActivation(
    modifier: Modifier = Modifier,
    listPerson: List<Person>,
    switchOnChange: (Boolean) -> Unit,
    switch : MutableState<Boolean>
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
                    switchON = switch,
                    switchOnChange = switchOnChange
                )
            }

            Divider(
                color = Gray200,
                modifier = Modifier.padding(vertical = BETWEEN_PADDING)
            )

            if (listPerson.isNotEmpty()){

                LazyRow {
                    items(listPerson) { person ->

                        Column(
                            modifier = Modifier
                                .padding(end = BETWEEN_PADDING , bottom = 10.dp, top = 6.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            if (person.image == null){

                                Icon(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .border(
                                            width = 1.dp,
                                            color = Gray200,
                                            RoundedCornerShape(100)
                                        )
                                        .padding(6.dp),
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "person",
                                    tint = Gray200,
                                )
                            } else{

                                Image(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(shape = RoundedCornerShape(100.dp)),
                                    painter = rememberAsyncImagePainter(
                                        model = person.image,
                                        contentScale = ContentScale.Crop
                                    ),
                                    contentDescription = "person",
                                    contentScale = ContentScale.Crop
                                )

                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                modifier = Modifier.width(60.dp),
                                text = person.fullName,
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

                //Spacer(modifier = Modifier.height(32.dp))

            }else{
                Text(
                    modifier = Modifier.padding(LARGE_PADDING),
                    text = "Empty List",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onSurface
                )
            }


        }

    }
}

@Preview
@Composable
fun BoxSendingListActivationPreview() {

   
}