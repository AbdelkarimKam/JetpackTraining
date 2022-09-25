package com.example.jetpacktrainning.data.repository

import android.util.Log
import com.example.jetpacktrainning.data.datasources.retrofit.ApiDataSource
import com.example.jetpacktrainning.data.datasources.retrofit.NetworkMapper
import com.example.jetpacktrainning.model.Country
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val networkMapper: NetworkMapper
) : IRemoteRepository {
    override suspend fun getCountries(): List<Country> {
        traceThreadName()
        val networkCountries = apiDataSource.getAllCountries()
        return networkMapper.mapFromEntityList(networkCountries.data)
    }

    override suspend fun getCountryById(id: Int): Country {
        traceThreadName()
        val networkCountries = apiDataSource.getCountryById(id)
        return networkMapper.mapFromEntity(networkCountries.data)
    }

    private fun traceThreadName() = Log.d(TAG, "Current Thread ${Thread.currentThread().name}")

    companion object {
        private const val TAG = "RemoteRepository"
    }
}