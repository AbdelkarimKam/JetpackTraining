package com.example.jetpacktrainning.ui.viewmodel

import androidx.lifecycle.*
import com.example.jetpacktrainning.model.Country
import com.example.jetpacktrainning.repository.MainRepository
import com.example.jetpacktrainning.tools.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val mainRepository: MainRepository
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
                    mainRepository.getCountries()
                        .onEach { _countriesState.value = it }
                        .launchIn(viewModelScope)
                }
                is MainStateEvent.GetCountryEvent -> {
                    mainRepository.getCountryById(mainStateEvent.id)
                        .onEach { _countryState.value = it }
                        .launchIn(viewModelScope)
                }
            }
        }
    }
}