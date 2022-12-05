package com.fall.fallout.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_persons")
data class Person(
    val fullName: String,
    @PrimaryKey
    val phoneNumber: String,
    val image: String,
)

class InvalidPersonException(message: String): Exception(message)
