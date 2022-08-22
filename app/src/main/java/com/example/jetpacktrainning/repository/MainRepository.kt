package com.example.jetpacktrainning.repository

import android.util.Log
import com.example.jetpacktrainning.model.Country
import com.example.jetpacktrainning.tools.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteRepository: IRemoteRepository,
    private val localRepository: ILocalRepository
) {
    suspend fun getCountries(): Flow<Resource<List<Country>>> = flow {
        emit(Resource.loading(null))

        try {
            traceThreadName()
            val countries = remoteRepository.getCountries()
            countries.forEach {
                localRepository.insertCountry(it)
            }
        } catch (e: Exception) {
            emit(Resource.error(null, message = e.message))
        }

        try {
            val cacheCountries = localRepository.getCountries()
            emit(Resource.success(cacheCountries))
        } catch (e: Exception) {
            emit(Resource.error(null, message = e.message))
        }
    }

    suspend fun getCountryById(id: Int): Flow<Resource<Country>> = flow {
        emit(Resource.loading(null))

        try {
            traceThreadName()
            val country = remoteRepository.getCountryById(id)
            localRepository.insertCountry(country)
        } catch (e: Exception) {
            emit(Resource.error(null, message = e.message))
        }

        try {
            val cacheCountry = localRepository.getCountryById(id)
            emit(Resource.success(cacheCountry))
        } catch (e: Exception) {
            emit(Resource.error(null, message = e.message))
        }
    }

    private fun traceThreadName() = Log.d(TAG,"Current Thread ${Thread.currentThread().name}")

    companion object {
        private const val TAG = "MainRepository"
    }
}