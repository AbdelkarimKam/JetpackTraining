package com.example.jetpacktrainning.ui.viewmodel

import androidx.lifecycle.*
import com.example.jetpacktrainning.model.Country
import com.example.jetpacktrainning.data.repository.CountryRepository
import com.example.jetpacktrainning.tools.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val countryRepository: CountryRepository
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
                countryRepository.getCountries()
                    .onEach {
                        _countriesState.emit(it)
                    }
                    .launchIn(viewModelScope)
            }
            is MainStateEvent.GetCountryEvent -> {
                countryRepository.getCountryById(mainStateEvent.id)
                    .onEach { _countryState.emit(it) }
                    .launchIn(viewModelScope)
            }
        }
    }
}