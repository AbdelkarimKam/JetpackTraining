package com.example.jetpacktrainning.data.datasources.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CountryCacheEntity::class],version = 1)
abstract class CountryDataBase : RoomDatabase(){
    abstract fun countryDao(): CountryDao

    companion object {
        const val DATABASE_NAME = "countries_db"
    }
}