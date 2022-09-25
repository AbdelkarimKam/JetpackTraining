package com.example.jetpacktrainning.data.repository

import com.example.jetpacktrainning.model.Country

interface ICountryRemoteRepository {
    suspend fun getCountries(): List<Country>
    suspend fun getCountryById(id :Int): Country
}