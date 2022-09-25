package com.example.jetpacktrainning.ui.viewmodel

import androidx.lifecycle.*
import com.example.jetpacktrainning.model.Country
import com.example.jetpacktrainning.domain.GetCountryByIdUseCase
import com.example.jetpacktrainning.domain.GetLatestCountriesUseCase
import com.example.jetpacktrainning.tools.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getLatestCountriesUseCase: GetLatestCountriesUseCase,
    private val getCountryByIdUseCase: GetCountryByIdUseCase
) : ViewModel() {

    private val _countriesState: MutableStateFlow<Resource<List<Country>>> by lazy {
        MutableStateFlow(Resource.loading(null))
    }

    val countriesState: StateFlow<Resource<List<Country>>>
        get() = _countriesState.asStateFlow()

    private val _countryState: MutableStateFlow<Resource<Country>> by lazy {
        MutableStateFlow(Resource.loading(null))
    }

    val countryState: StateFlow<Resource<Country>>
        get() = _countryState.asStateFlow()

    init {
        setStateEvent(MainStateEvent.GetCountriesEvent)
    }

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        when (mainStateEvent) {
            is MainStateEvent.GetCountriesEvent -> {
                getLatestCountriesUseCase.invoke()
                    .onEach { _countriesState.emit(it) }
                    .launchIn(viewModelScope)
            }
            is MainStateEvent.GetCountryEvent -> {
                getCountryByIdUseCase.invoke(mainStateEvent.id)
                    .onEach { _countryState.emit(it) }
                    .launchIn(viewModelScope)
            }
        }
    }
}