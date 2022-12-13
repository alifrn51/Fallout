package com.fall.fallout.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tbl_persons")
data class Person(
    val fullName: String,
    @PrimaryKey
    val phoneNumber: String,
    val image: String?,
): Serializable

class InvalidPersonException(message: String): Exception(message)
