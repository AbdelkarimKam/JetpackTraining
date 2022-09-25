package com.example.jetpacktrainning.domain

import com.example.jetpacktrainning.data.repository.ICountryLocalRepository
import com.example.jetpacktrainning.data.repository.ICountryRemoteRepository
import com.example.jetpacktrainning.model.Country
import com.example.jetpacktrainning.tools.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetLatestCountriesUseCase(
    private val remoteRepository: ICountryRemoteRepository,
    private val localRepository: ICountryLocalRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<Resource<List<Country>>> = flow {
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
}
