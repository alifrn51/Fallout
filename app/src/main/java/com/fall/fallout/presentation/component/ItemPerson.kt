package com.fall.fallout.presentation.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fall.fallout.R
import com.fall.fallout.ui.theme.BETWEEN_PADDING
import com.fall.fallout.ui.theme.CARD_ITEM_ROUNDED
import com.fall.fallout.ui.theme.FalloutTheme
import com.fall.fallout.ui.theme.SMALL_PADDING

@Composable
fun ItemPerson(
    modifier: Modifier = Modifier,
    fullName: String,
    phoneNumber: String,
    image: Painter,
    onClickDeleteItemPerson: () -> Unit = {},
    onClickEditItemPerson: () -> Unit = {}
) {


    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = SMALL_PADDING, end = SMALL_PADDING, bottom = BETWEEN_PADDING),

        shape = RoundedCornerShape(CARD_ITEM_ROUNDED)
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SMALL_PADDING),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(100)),
                painter = image,
                contentScale = ContentScale.Crop,
                contentDescription = "ImagePerson"
            )

            Spacer(modifier = Modifier.width(BETWEEN_PADDING))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceAround
            ) {


                Text(text = fullName, style = MaterialTheme.typography.body1)
                Text(text = phoneNumber, style = MaterialTheme.typography.caption)
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .padding(2.dp)
                        .clickable {
                            onClickEditItemPerson()
                        }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .padding(2.dp)
                        .clickable {
                            onClickDeleteItemPerson()
                        }
                )
            }
        }


    }


}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ItemPersonPreview() {

    FalloutTheme {

        ItemPerson(
            fullName = "Full name",
            phoneNumber = "09397859149",
            image = painterResource(id = R.drawable.sample)
        )
    }
}