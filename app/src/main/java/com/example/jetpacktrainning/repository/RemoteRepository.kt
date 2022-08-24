package com.example.jetpacktrainning.repository

import android.util.Log
import com.example.jetpacktrainning.data.retrofit.ApiCountries
import com.example.jetpacktrainning.data.retrofit.NetworkMapper
import com.example.jetpacktrainning.data.retrofit.model.DataListResponse
import com.example.jetpacktrainning.data.retrofit.model.DataResponse
import com.example.jetpacktrainning.model.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val apiCountries: ApiCountries,
    private val networkMapper: NetworkMapper,
    private val ioDispatcher: CoroutineDispatcher
) : IRemoteRepository {
    override suspend fun getCountries(): List<Country> {
        val networkCountries: DataListResponse
        withContext(ioDispatcher) {
            traceThreadName()
            networkCountries = apiCountries.getAllCountries()
        }
        return networkMapper.mapFromEntityList(networkCountries.data)
    }

    override suspend fun getCountryById(id: Int): Country {
        val networkCountries: DataResponse
        withContext(ioDispatcher) {
            traceThreadName()
            networkCountries = apiCountries.getCountryById(id)
        }
        return networkMapper.mapFromEntity(networkCountries.data)
    }

    private fun traceThreadName() = Log.d(TAG, "Current Thread ${Thread.currentThread().name}")

    companion object {
        private const val TAG = "RemoteRepository"
    }
}