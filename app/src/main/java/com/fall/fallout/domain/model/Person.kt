package com.fall.fallout.domain.model

import androidx.compose.ui.graphics.painter.Painter

data class Person(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val image: Painter
)
