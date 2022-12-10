package com.fall.fallout.presentation.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.fall.fallout.R
import com.fall.fallout.ui.theme.*

@Composable
fun ItemPerson(
    modifier: Modifier = Modifier,
    fullName: String,
    phoneNumber: String,
    image: String?,
    onClickDeleteItemPerson: () -> Unit,
    onClickEditItemPerson: () -> Unit
) {

    val imageUri by remember {
        mutableStateOf<Uri?>(if (image == null) null else Uri.parse(image))
    }

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


            if (imageUri == null){

                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .border(width = 1.dp , color = Gray200, RoundedCornerShape(100))
                        .padding(6.dp),
                        imageVector = Icons.Default.Person,
                        tint = Gray200,
                    contentDescription = "ImagePerson"
                )

            }else{
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(100)),
                    painter = rememberAsyncImagePainter(
                        model = imageUri,
                        contentScale = ContentScale.Crop
                    ) ,
                    contentScale = ContentScale.Crop,
                    contentDescription = "ImagePerson"
                )
            }



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

                IconButton(onClick = {
                    onClickEditItemPerson() }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .padding(2.dp)
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                IconButton(onClick = {
                    onClickDeleteItemPerson()
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .padding(2.dp)
                    )
                }

            }
        }


    }


}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ItemPersonPreview() {

    FalloutTheme {

        /*ItemPerson(
            fullName = "Full name",
            phoneNumber = "09397859149",
            image = painterResource(id = R.drawable.sample)
        )*/
    }
}