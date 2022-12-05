package com.fall.fallout.domain.use_cases

import com.fall.fallout.domain.model.Person
import com.fall.fallout.domain.repository.PersonRepository

class DeletePersonUseCase(
    private val repository: PersonRepository
) {

    suspend operator fun invoke(person: Person){
        repository.deletePerson(person)
    }
}