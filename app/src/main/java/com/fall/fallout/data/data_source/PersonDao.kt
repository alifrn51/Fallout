package com.fall.fallout.data.data_source

import androidx.room.*
import com.fall.fallout.domain.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Query("SELECT * FROM tbl_persons")
    fun getPersons(): Flow<List<Person>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPerson(person: Person)

    @Delete
    suspend fun deletePerson(person: Person)

    @Update
    suspend fun updatePerson(person: Person)

}