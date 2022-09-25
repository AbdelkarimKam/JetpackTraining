package com.example.jetpacktrainning.data.datasources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(countryEntity: CountryCacheEntity):Long  // return negative Long on error

    @Query("SELECT * FROM countries")
    suspend fun getCountries(): List<CountryCacheEntity>

    @Query("SELECT * FROM countries WHERE id =:id")
    suspend fun getCountryById(id : Int): CountryCacheEntity
}