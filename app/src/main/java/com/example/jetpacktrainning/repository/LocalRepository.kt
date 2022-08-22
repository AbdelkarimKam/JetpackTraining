package com.example.jetpacktrainning.repository

import com.example.jetpacktrainning.data.room.CacheMapper
import com.example.jetpacktrainning.data.room.CountryDao
import com.example.jetpacktrainning.model.Country
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val countryDao: CountryDao,
    private val cacheMapper: CacheMapper
) : ILocalRepository {
    override suspend fun insertCountry(country: Country): Long {
        return countryDao.insert(cacheMapper.mapToEntity(country))
    }

    override suspend fun getCountryById(id: Int): Country {
        val cacheCountry = countryDao.getCountryById(id)
        return cacheMapper.mapFromEntity(cacheCountry)
    }

    override suspend fun getCountries(): List<Country> {
        val cacheCountries = countryDao.getCountries()
        return cacheMapper.mapFromEntityList(cacheCountries)
    }


}