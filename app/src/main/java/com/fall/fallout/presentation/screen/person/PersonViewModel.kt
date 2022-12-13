package com.fall.fallout.presentation.screen.person

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fall.fallout.domain.model.InvalidPersonException
import com.fall.fallout.domain.model.Person
import com.fall.fallout.domain.use_cases.GetPersonsUseCase
import com.fall.fallout.domain.use_cases.PersonUseCases
import com.fall.fallout.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val personsUseCase: PersonUseCases
) :ViewModel() {


    private val _state = mutableStateOf(PersonListState())
    val state: State<PersonListState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var recentlyDeletedPerson: Person? = null

    private var getPersonJob: Job? = null

    init {
        getPerson()
    }


    fun onEvent(event: PersonListEvent){
        when(event){

            is PersonListEvent.CurrentPersonSelected -> {
                _state.value = state.value.copy(
                    personSelected = event.person
                )
            }
            is PersonListEvent.EditPerson -> {
                viewModelScope.launch {
                    try {
                        personsUseCase.updatePersonUseCase(
                            person = Person(
                                fullName = event.fullName,
                                phoneNumber = event.phoneNumber,
                                image = event.image
                            )
                        )

                        _state.value = state.value.copy(persons = personsUseCase.getPersonsUseCase().stateIn(viewModelScope).value)

                        _eventFlow.emit(UiEvent.EditPerson)
                    }catch (e: InvalidPersonException){
                        _eventFlow.emit(UiEvent.ShowSnackbar(
                            message = e.message ?: "Couldn't add contact"
                        ))
                    }
                }
            }

            is PersonListEvent.DeletePerson -> {

                viewModelScope.launch {
                    personsUseCase.deletePersonUseCase(person = state.value.personSelected?:return@launch)
                    recentlyDeletedPerson = state.value.personSelected
                }
            }

            is PersonListEvent.RestorePerson -> {

                viewModelScope.launch {
                    personsUseCase.addPersonUseCase(person = recentlyDeletedPerson ?: return@launch)
                    recentlyDeletedPerson = null
                }

            }

            is PersonListEvent.AddPerson -> {

                viewModelScope.launch {
                    try {
                        personsUseCase.addPersonUseCase(
                            person = Person(
                                fullName = event.fullName,
                                phoneNumber = event.phoneNumber,
                                image = event.image
                            )
                        )

                            _state.value = state.value.copy(persons = personsUseCase.getPersonsUseCase().stateIn(viewModelScope).value)

                        _eventFlow.emit(UiEvent.AddPerson)
                    }catch (e: InvalidPersonException){
                        _eventFlow.emit(UiEvent.ShowSnackbar(
                            message = e.message ?: "Couldn't add contact"
                        ))
                    }
                }

            }
        }
    }

    private fun getPerson(){
        getPersonJob?.cancel();
        getPersonJob = personsUseCase.getPersonsUseCase()
            .onEach { persons ->
                _state.value = state.value.copy(persons = persons)
            }
            .launchIn(viewModelScope)
    }
}