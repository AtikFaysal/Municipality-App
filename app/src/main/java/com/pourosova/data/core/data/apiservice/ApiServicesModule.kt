package com.pourosova.data.core.data.apiservice

import com.pourosova.data.core.di.qualifier.AppBaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServicesModule {

    @Provides
    @Singleton
    fun provideCredentialApiService(
        @AppBaseUrl retrofit: Retrofit,
        authRefreshServiceHolder: AuthRefreshServiceHolder
    ): ApiServices {
        authRefreshServiceHolder.setAuthRefreshApi(retrofit.create(ApiServices::class.java))
        return retrofit.create(ApiServices::class.java)
    }
}