package com.fall.fallout.utils

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    object AddPerson : UiEvent()
}