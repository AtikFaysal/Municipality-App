package com.pourosova.data.core.di

import com.pourosova.data.core.data.repoimpl.AppRepoImpl
import com.pourosova.data.core.domain.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindCredentialRepository(repoImpl: AppRepoImpl): AppRepository
}