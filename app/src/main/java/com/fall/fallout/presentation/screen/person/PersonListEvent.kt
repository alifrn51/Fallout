package com.fall.fallout.presentation.screen.person

import com.fall.fallout.domain.model.Person

sealed class PersonListEvent{

    data class CurrentPersonSelected(val person: Person): PersonListEvent()
    object EditPerson: PersonListEvent()
    object DeletePerson: PersonListEvent()
    data class AddPerson(val fullName: String,val phoneNumber: String,val image: String?): PersonListEvent()
    object RestorePerson: PersonListEvent()

}
