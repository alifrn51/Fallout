package com.fall.fallout.domain.use_cases

import com.fall.fallout.domain.model.InvalidPersonException
import com.fall.fallout.domain.model.Person
import com.fall.fallout.domain.repository.PersonRepository

class AddPersonUseCase(
    private val repository: PersonRepository
) {


    @Throws(InvalidPersonException::class)
    suspend operator fun invoke(person: Person) {

        if(person.fullName.isBlank()){
            throw InvalidPersonException("The full name of the contact can't be empty.")
        }
        if (person.phoneNumber.isBlank()){
            throw InvalidPersonException("The phone number of the contact can't be empty.")
        }
        repository.addPerson(person)
    }
}