package com.example.jetpacktrainning.repository

import com.example.jetpacktrainning.model.Country

interface IRemoteRepository {
    suspend fun getAllCountries(): List<Country>
    suspend fun getCountryById(id :Int): Country
}