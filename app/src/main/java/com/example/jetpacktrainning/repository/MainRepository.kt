package com.example.jetpacktrainning.repository

import com.example.jetpacktrainning.model.Country
import com.example.jetpacktrainning.tools.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteRepository: IRemoteRepository,
    private val localRepository: ILocalRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getCountries(): Flow<Resource<List<Country>>> = flow {
        emit(Resource.loading(null))

        try {
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
    }.flowOn(ioDispatcher)


    suspend fun getCountryById(id: Int): Flow<Resource<Country>> = flow {
        emit(Resource.loading(null))

        try {
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
    }.flowOn(ioDispatcher)
}