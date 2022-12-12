package com.fall.fallout.presentation.screen.main

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fall.fallout.domain.use_cases.PersonUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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

    var currentLocation: Boolean = false

    private var getPersonJob: Job? = null


    init {
        getPersons()
    }

    fun onEvent(event: MainEvent){
        when(event){

            is MainEvent.UpdateLocation -> {
                _state.value = state.value.copy(
                    latLong = event.latLong
                )
            }
            is MainEvent.SensorActivation -> {
                _state.value = state.value.copy(
                    switchSensor = event.isEnable
                )
            }

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

        fun toggleCurrentLocation ():Boolean{
        return !currentLocation
    }

}