package com.fall.fallout.domain.use_cases

import com.fall.fallout.domain.model.Person
import com.fall.fallout.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPersonsUseCase(
    private val repository: PersonRepository
) {

    operator fun invoke(): Flow<List<Person>>{
        return repository.getPersons()
    }

}