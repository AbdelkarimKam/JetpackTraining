package com.example.jetpacktrainning.repository

import com.example.jetpacktrainning.data.retrofit.model.DataListResponse
import com.example.jetpacktrainning.data.retrofit.model.DataResponse
import com.example.jetpacktrainning.tools.EntityMapper

interface ICountriesRepository {
    suspend fun getAllCountries(): DataListResponse
    suspend fun getCountryById(id :Int): DataResponse
}