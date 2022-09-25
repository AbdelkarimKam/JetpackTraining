package com.example.jetpacktrainning.data.datasources.room

import com.example.jetpacktrainning.model.Country
import com.example.jetpacktrainning.tools.EntityMapper
import javax.inject.Inject

class CacheMapper @Inject constructor() : EntityMapper<CountryCacheEntity, Country> {
    override fun mapFromEntity(entity: CountryCacheEntity): Country {
        return Country(
            countryId = entity.id,
            countryCode = entity.code,
            continent = entity.continent,
            name = entity.name
        )
    }

    override fun mapToEntity(domaineModel: Country): CountryCacheEntity {
        return CountryCacheEntity(
            id = domaineModel.countryId,
            code = domaineModel.countryCode,
            continent = domaineModel.continent,
            name = domaineModel.name
        )
    }

    fun mapFromEntityList(entities: List<CountryCacheEntity>): List<Country> {
        return entities.map { mapFromEntity(it) }
    }
}