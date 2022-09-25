package com.example.jetpacktrainning.di

import com.example.jetpacktrainning.data.datasources.retrofit.ApiDataSource
import com.example.jetpacktrainning.data.datasources.retrofit.NetworkMapper
import com.example.jetpacktrainning.data.datasources.room.CacheMapper
import com.example.jetpacktrainning.data.datasources.room.CountryDao
import com.example.jetpacktrainning.data.repository.CountriesCountryLocalRepository
import com.example.jetpacktrainning.data.repository.CountryCountryRemoteRepository
import com.example.jetpacktrainning.data.repository.ICountryLocalRepository
import com.example.jetpacktrainning.data.repository.ICountryRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideCountryRemoteRepository(
        apiDataSource: ApiDataSource,
        networkMapper: NetworkMapper
    ): ICountryRemoteRepository {
        return CountryCountryRemoteRepository(
            apiDataSource, networkMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideCountriesLocalRepository(
        countryDao: CountryDao,
        cacheMapper: CacheMapper
    ): ICountryLocalRepository {
        return CountriesCountryLocalRepository(
            countryDao, cacheMapper
        )
    }
}
