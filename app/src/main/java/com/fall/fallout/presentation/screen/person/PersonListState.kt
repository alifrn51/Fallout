package com.fall.fallout.presentation.screen.person

import com.fall.fallout.domain.model.Person

data class PersonListState(
    val personSelected: Person? = null,
    val persons: List<Person> = emptyList(),
)
