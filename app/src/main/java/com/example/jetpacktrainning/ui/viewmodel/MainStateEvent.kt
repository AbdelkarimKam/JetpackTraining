package com.example.jetpacktrainning.ui.viewmodel

sealed class MainStateEvent {
    object GetCountriesEvent : MainStateEvent()
    data class GetCountryEvent(val id: Int) : MainStateEvent()
}
