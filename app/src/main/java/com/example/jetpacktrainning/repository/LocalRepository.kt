package com.example.jetpacktrainning.repository

import android.util.Log
import com.example.jetpacktrainning.data.room.CacheMapper
import com.example.jetpacktrainning.data.room.CountryCacheEntity
import com.example.jetpacktrainning.data.room.CountryDao
import com.example.jetpacktrainning.model.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val countryDao: CountryDao,
    private val cacheMapper: CacheMapper,
    private val ioDispatcher: CoroutineDispatcher
) : ILocalRepository {
    override suspend fun insertCountry(country: Country): Long {
        return withContext(ioDispatcher) {
            traceThreadName()
            countryDao.insert(cacheMapper.mapToEntity(country))
        }
    }

    override suspend fun getCountryById(id: Int): Country {
        val cacheCountry: CountryCacheEntity
        withContext(ioDispatcher) {
            traceThreadName()
            cacheCountry = countryDao.getCountryById(id)
        }
        return cacheMapper.mapFromEntity(cacheCountry)
    }

    override suspend fun getCountries(): List<Country> {
        val cacheCountries: List<CountryCacheEntity>
        withContext(ioDispatcher) {
            traceThreadName()
            cacheCountries = countryDao.getCountries()
        }
        return cacheMapper.mapFromEntityList(cacheCountries)
    }

    private fun traceThreadName() = Log.d(TAG, "Current Thread ${Thread.currentThread().name}")

    companion object {
        private const val TAG = "LocalRepository"
    }
}