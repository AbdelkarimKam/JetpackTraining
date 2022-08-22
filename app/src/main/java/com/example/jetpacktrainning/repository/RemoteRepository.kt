package com.example.jetpacktrainning.repository

import com.example.jetpacktrainning.data.retrofit.ApiCountries
import com.example.jetpacktrainning.data.retrofit.NetworkMapper
import com.example.jetpacktrainning.model.Country
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val apiCountries: ApiCountries,
    private val networkMapper: NetworkMapper
) :  IRemoteRepository{
    override suspend fun getCountries(): List<Country> {
        val networkCountries = apiCountries.getAllCountries()
        return networkMapper.mapFromEntityList(networkCountries.data)
    }

    override suspend fun getCountryById(id: Int): Country {
        val networkCountries = apiCountries.getCountryById(id)
        return networkMapper.mapFromEntity(networkCountries.data)    }

}