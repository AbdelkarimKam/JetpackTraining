package com.example.jetpacktrainning.di

import com.example.jetpacktrainning.data.retrofit.ApiCountries
import com.example.jetpacktrainning.data.retrofit.NetworkMapper
import com.example.jetpacktrainning.data.room.CacheMapper
import com.example.jetpacktrainning.data.room.CountryDao
import com.example.jetpacktrainning.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideRemoteRepository(
        apiCountries: ApiCountries,
        networkMapper: NetworkMapper
    ): IRemoteRepository {
        return RemoteRepository(
            apiCountries, networkMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideLocalRepository(
        countryDao: CountryDao,
        cacheMapper: CacheMapper
    ): ILocalRepository {
        return LocalRepository(
            countryDao, cacheMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideMainRepository(
        remoteRepository: IRemoteRepository,
        localRepository: ILocalRepository,
        ioDispatcher: CoroutineDispatcher
    ): MainRepository {
        return MainRepository(
            remoteRepository, localRepository, ioDispatcher
        )
    }
}
