package com.example.jetpacktrainning.di

import com.example.jetpacktrainning.data.datasources.retrofit.ApiDataSource
import com.example.jetpacktrainning.data.datasources.retrofit.NetworkMapper
import com.example.jetpacktrainning.data.datasources.room.CacheMapper
import com.example.jetpacktrainning.data.datasources.room.CountryDao
import com.example.jetpacktrainning.data.repository.*
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
        apiDataSource: ApiDataSource,
        networkMapper: NetworkMapper
    ): IRemoteRepository {
        return RemoteRepository(
            apiDataSource, networkMapper
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
    ): CountryRepository {
        return CountryRepository(
            remoteRepository, localRepository, ioDispatcher
        )
    }
}
