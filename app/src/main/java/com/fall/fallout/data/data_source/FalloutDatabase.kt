package com.fall.fallout.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fall.fallout.domain.model.Person

@Database(
    entities = [Person::class],
    version = 1,
    exportSchema = false
)
abstract class FalloutDatabase: RoomDatabase() {

    abstract fun daoPerson(): PersonDao

    companion object{
        const val DATABASE_NAME = "fallout.db"
    }
}