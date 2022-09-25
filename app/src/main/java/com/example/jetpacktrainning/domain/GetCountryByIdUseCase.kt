package com.example.jetpacktrainning.domain

import com.example.jetpacktrainning.data.repository.ICountryLocalRepository
import com.example.jetpacktrainning.data.repository.ICountryRemoteRepository
import com.example.jetpacktrainning.model.Country
import com.example.jetpacktrainning.tools.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetCountryByIdUseCase(
    private val remoteRepository: ICountryRemoteRepository,
    private val localRepository: ICountryLocalRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(id: Int): Flow<Resource<Country>> = flow {
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
