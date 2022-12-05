package com.fall.fallout.domain.use_cases

data class PersonUseCases(
    val getPersonsUseCase: GetPersonsUseCase,
    val addPersonUseCase: AddPersonUseCase,
    val deletePersonUseCase: DeletePersonUseCase,
    val updatePersonUseCase: UpdatePersonUseCase
)
