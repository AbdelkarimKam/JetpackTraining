package com.example.jetpacktrainning.repository

import android.util.Log
import com.example.jetpacktrainning.data.room.CacheMapper
import com.example.jetpacktrainning.data.room.CountryCacheEntity
import com.example.jetpacktrainning.data.room.CountryDao
import com.example.jetpacktrainning.model.Country
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val countryDao: CountryDao,
    private val cacheMapper: CacheMapper
) : ILocalRepository {
    override suspend fun insertCountry(country: Country): Long {
        traceThreadName()
        return countryDao.insert(cacheMapper.mapToEntity(country))
    }

    override suspend fun getCountryById(id: Int): Country {
        traceThreadName()
        val cacheCountry: CountryCacheEntity = countryDao.getCountryById(id)
        return cacheMapper.mapFromEntity(cacheCountry)
    }

    override suspend fun getCountries(): List<Country> {
        traceThreadName()
        val cacheCountries = countryDao.getCountries()
        return cacheMapper.mapFromEntityList(cacheCountries)
    }

    private fun traceThreadName() = Log.d(TAG, "Current Thread ${Thread.currentThread().name}")

    companion object {
        private const val TAG = "LocalRepository"
    }
}