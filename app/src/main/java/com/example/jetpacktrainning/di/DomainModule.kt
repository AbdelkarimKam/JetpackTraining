package com.example.jetpacktrainning.di

import com.example.jetpacktrainning.data.repository.ICountryLocalRepository
import com.example.jetpacktrainning.data.repository.ICountryRemoteRepository
import com.example.jetpacktrainning.domain.GetCountryByIdUseCase
import com.example.jetpacktrainning.domain.GetLatestCountriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @ViewModelScoped
    @Provides
    fun provideGetLatestCountriesUseCase(
        countryRemoteRepository: ICountryRemoteRepository,
        countryLocalRepository: ICountryLocalRepository,
        ioDispatcher: CoroutineDispatcher
    ): GetLatestCountriesUseCase {
        return GetLatestCountriesUseCase(
            countryRemoteRepository, countryLocalRepository, ioDispatcher
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetCountryByIdUseCase(
        countryRemoteRepository: ICountryRemoteRepository,
        countryLocalRepository: ICountryLocalRepository,
        ioDispatcher: CoroutineDispatcher
    ): GetCountryByIdUseCase {
        return GetCountryByIdUseCase(
            countryRemoteRepository, countryLocalRepository, ioDispatcher
        )
    }
}
