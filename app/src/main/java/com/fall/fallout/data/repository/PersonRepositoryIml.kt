package com.fall.fallout.data.repository

import com.fall.fallout.data.data_source.PersonDao
import com.fall.fallout.domain.model.Person
import com.fall.fallout.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class PersonRepositoryIml(
    private val dao: PersonDao
): PersonRepository {

    override fun getPersons(): Flow<List<Person>> {
        return dao.getPersons()
    }

    override suspend fun addPerson(person: Person) {
        dao.addPerson(person = person)
    }

    override suspend fun deletePerson(person: Person) {
        dao.deletePerson(person = person)
    }

    override suspend fun updatePerson(person: Person) {
        dao.updatePerson(person = person)
    }
}