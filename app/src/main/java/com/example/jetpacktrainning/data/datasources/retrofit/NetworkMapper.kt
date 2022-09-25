package com.example.jetpacktrainning.data.datasources.retrofit

import com.example.jetpacktrainning.data.datasources.retrofit.model.CountryResponse
import com.example.jetpacktrainning.model.Country
import com.example.jetpacktrainning.tools.EntityMapper
import javax.inject.Inject

class NetworkMapper @Inject constructor() : EntityMapper<CountryResponse, Country> {

    override fun mapFromEntity(entity: CountryResponse): Country {
        return Country(
            continent = entity.continent,
            countryCode = entity.countryCode,
            countryId = entity.countryId,
            name = entity.name
        )
    }

    override fun mapToEntity(domaineModel: Country): CountryResponse {
        return CountryResponse(
            continent = domaineModel.continent,
            countryCode = domaineModel.countryCode,
            countryId = domaineModel.countryId,
            name = domaineModel.name
        )
    }

    fun mapFromEntityList(entities: List<CountryResponse>): List<Country> {
        return entities.map {
            mapFromEntity(it)
        }
    }
}