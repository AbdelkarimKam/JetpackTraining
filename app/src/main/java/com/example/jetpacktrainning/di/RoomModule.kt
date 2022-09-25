package com.example.jetpacktrainning.di

import android.content.Context
import androidx.room.Room
import com.example.jetpacktrainning.data.datasources.room.CountryDao
import com.example.jetpacktrainning.data.datasources.room.CountryDataBase
import com.example.jetpacktrainning.data.datasources.room.CountryDataBase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideCountryDataBase(@ApplicationContext context: Context): CountryDataBase {
        return Room.databaseBuilder(
            context,
            CountryDataBase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCountryDao(countryDataBase: CountryDataBase): CountryDao {
        return countryDataBase.countryDao()
    }
}