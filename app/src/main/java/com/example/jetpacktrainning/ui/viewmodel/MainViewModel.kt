package com.example.jetpacktrainning.ui.viewmodel

import androidx.lifecycle.*
import com.example.jetpacktrainning.model.Country
import com.example.jetpacktrainning.data.repository.CountryRepository
import com.example.jetpacktrainning.tools.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val countryRepository: CountryRepository
) : ViewModel() {

    private val _countriesState: MutableLiveData<Resource<List<Country>>> by lazy {
        MutableLiveData<Resource<List<Country>>>().also {
            setStateEvent(MainStateEvent.GetCountriesEvent)
        }
    }

    val countriesState: LiveData<Resource<List<Country>>>
        get() = _countriesState

    private val _countryState: MutableLiveData<Resource<Country>> by lazy {
        MutableLiveData<Resource<Country>>()
    }

    val countryState: LiveData<Resource<Country>>
        get() = _countryState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetCountriesEvent -> {
                    countryRepository.getCountries()
                        .onEach { _countriesState.value = it }
                }
                is MainStateEvent.GetCountryEvent -> {
                    countryRepository.getCountryById(mainStateEvent.id)
                        .onEach { _countryState.value = it }
                }
            }
        }
    }
}