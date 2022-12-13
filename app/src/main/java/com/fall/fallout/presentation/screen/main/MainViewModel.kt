package com.fall.fallout.presentation.screen.main

import android.telephony.SmsManager
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fall.fallout.domain.use_cases.PersonUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*
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
            is MainEvent.ContactActivation -> {
                _state.value = state.value.copy(
                    switchContact = event.isEnable
                )
            }
            is MainEvent.SendSMS -> {


                val sms = SmsManager.getDefault()
                _state.value.persons.map { person ->
                    sms.sendTextMessage(
                        person.phoneNumber, null,
                        messageSms(
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                            SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()),
                            "https://www.google.com/maps?q=" + _state.value.latLong?.latitude + "," + state.value.latLong?.longitude
                        ), null, null
                    )

                }

            }


        }
    }

    private fun messageSms(currentDate: String, currentTime: String, latLng: String): String {
        var message = "FALL DETECTED!" +
                "<br />" +
                "Time: " + currentTime +
                "<br />" +
                "Date: " + currentDate +
                "<br />" +
                "Coordination: " + latLng
        val lineSep = System.getProperty("line.separator")
        message = lineSep?.let { message.replace("<br />".toRegex(), it) }.toString()
        return message
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