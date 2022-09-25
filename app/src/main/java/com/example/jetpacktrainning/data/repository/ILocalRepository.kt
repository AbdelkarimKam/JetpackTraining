package com.example.jetpacktrainning.data.repository

import com.example.jetpacktrainning.model.Country

interface ILocalRepository {
    suspend fun insertCountry(country: Country):Long
    suspend fun getCountryById(id: Int): Country
    suspend fun getCountries(): List<Country>
}