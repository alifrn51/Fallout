package com.fall.fallout.domain.repository

import com.fall.fallout.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {

    fun getPersons(): Flow<List<Person>>

    suspend fun addPerson(person: Person)

    suspend fun deletePerson(person: Person)

    suspend fun updatePerson(person: Person)
}