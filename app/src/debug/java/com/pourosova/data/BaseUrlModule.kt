package com.pourosova.data

import com.pourosova.data.core.di.qualifier.AppBaseUrl
import com.pourosova.data.core.di.qualifier.AppImageBaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class BaseUrlModule{

    @Provides
    @AppBaseUrl
    fun provideBaseUrl():String = "https://esmart.redmoit.com/api/"

    @Provides
    @AppImageBaseUrl
    fun provideImageBaseUrl():String = "https://esmart.redmoit.com/"
}

