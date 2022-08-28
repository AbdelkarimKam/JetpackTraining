package com.example.jetpacktrainning.repository

import android.util.Log
import com.example.jetpacktrainning.data.retrofit.ApiCountries
import com.example.jetpacktrainning.data.retrofit.NetworkMapper
import com.example.jetpacktrainning.model.Country
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val apiCountries: ApiCountries,
    private val networkMapper: NetworkMapper
) : IRemoteRepository {
    override suspend fun getCountries(): List<Country> {
        traceThreadName()
        val networkCountries = apiCountries.getAllCountries()
        return networkMapper.mapFromEntityList(networkCountries.data)
    }

    override suspend fun getCountryById(id: Int): Country {
        traceThreadName()
        val networkCountries = apiCountries.getCountryById(id)
        return networkMapper.mapFromEntity(networkCountries.data)
    }

    private fun traceThreadName() = Log.d(TAG, "Current Thread ${Thread.currentThread().name}")

    companion object {
        private const val TAG = "RemoteRepository"
    }
}