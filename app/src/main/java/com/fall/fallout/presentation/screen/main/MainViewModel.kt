package com.fall.fallout.presentation.screen.main

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fall.fallout.domain.use_cases.PersonUseCases
import com.fall.fallout.presentation.screen.person.PersonListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val personsUseCase: PersonUseCases
): ViewModel() {


    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state

    private var getPersonJob: Job? = null


    init {
        getPersons()
    }

    fun onEvent(event: MainEvent){
        when(event){

        }
    }

    private fun getPersons(){
        getPersonJob?.cancel();
        getPersonJob = personsUseCase.getPersonsUseCase()
            .onEach { persons ->
                _state.value = state.value.copy(persons = persons)
            }
            .launchIn(viewModelScope)
    }

}