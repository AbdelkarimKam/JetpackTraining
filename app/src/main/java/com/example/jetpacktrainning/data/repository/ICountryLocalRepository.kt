package com.example.jetpacktrainning.data.repository

import com.example.jetpacktrainning.model.Country

interface ICountryLocalRepository {
    suspend fun insertCountry(country: Country):Long
    suspend fun getCountryById(id: Int): Country
    suspend fun getCountries(): List<Country>
}