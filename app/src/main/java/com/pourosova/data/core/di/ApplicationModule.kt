package com.pourosova.data.core.di

import android.content.Context
import com.pourosova.data.core.di.authrefresh.AuthRefreshServiceHolder
import com.pourosova.data.sharedpref.SharedPrefHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    fun sharePrefHelper(@ApplicationContext context: Context): SharedPrefHelper =
        SharedPrefHelper(context)

    @Provides
    @Singleton
    fun provideAuthRefreshServiceHolder() : AuthRefreshServiceHolder =
        AuthRefreshServiceHolder()
}