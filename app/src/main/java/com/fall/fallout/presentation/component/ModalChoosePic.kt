package com.fall.fallout.presentation.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrowseGallery
import androidx.compose.material.icons.outlined.BrowseGallery
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fall.fallout.R
import com.fall.fallout.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ModalChoosePic(
    onClickGallery: () -> Unit,
    onClickCamera: () -> Unit,
    contentScopes: @Composable (state: ModalBottomSheetState,scope: CoroutineScope) -> Unit
) {


    //Lets define bottomSheetScaffoldState which will hold the state of Scaffold
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()






    ModalBottomSheetLayout(
        sheetState =bottomSheetState ,
        sheetContent = {
            //Ui for bottom sheet

            Column(
                Modifier
                    .padding(LARGE_PADDING)
            ) {

                Spacer(modifier = Modifier.height(BETWEEN_PADDING))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {


                    IconButton(onClick = {

                        scope.launch {
                            bottomSheetState.animateTo(ModalBottomSheetValue.Hidden, tween(300))

                            onClickGallery()
                        }

                    }) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_outline_photo_library),
                                contentDescription = "Gallery",
                                modifier = Modifier.size(44.dp),
                                tint = MaterialTheme.colors.primary

                            )

                            Spacer(modifier = Modifier.height(BETWEEN_PADDING))

                            Text(text = "Gallery")

                        }

                    }

                    /*//Spacer(modifier = Modifier.width(LARGE_PADDING))
                    IconButton(onClick = {

                        scope.launch {
                            bottomSheetState.animateTo(ModalBottomSheetValue.Hidden, tween(300))

                            onClickCamera()
                        }


                    }) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Camera,
                                contentDescription = "Camera",
                                modifier = Modifier.size(44.dp),
                                tint = MaterialTheme.colors.primary
                            )
                            Spacer(modifier = Modifier.height(BETWEEN_PADDING))

                            Text(text = "Camera")

                        }

                    }*/

                }

                Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SIZE_HEIGHT_BUTTON),
                    shape = RoundedCornerShape(CARD_ITEM_ROUNDED),
                    elevation = ButtonDefaults.elevation(0.dp),
                    border = BorderStroke(1.dp, color = Gray200),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                    onClick = {
                        scope.launch {
                            bottomSheetState.animateTo(ModalBottomSheetValue.Hidden, tween(300))
                        }
                    },

                    ) {

                    Text(text = "Cancel", color = White)

                }

            }


        },
        sheetElevation = 0.dp,
        sheetShape = RoundedCornerShape(CARD_DIALOG_ROUNDED),
        sheetBackgroundColor = MaterialTheme.colors.surface,
        scrimColor = Black.copy(alpha = 0.7f)
    ) {

        contentScopes(bottomSheetState,scope)
    }

}

@Preview
@Composable
fun DialogChoosePicPreview() {

}