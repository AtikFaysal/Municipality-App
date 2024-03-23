package com.pourosova.data.base

import android.app.Application
import com.pourosova.data.core.designsystem.FontsOverride
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import com.pourosova.data.BuildConfig

@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        FontsOverride.setDefaultFont(this, "MONOSPACE", FontsOverride.regularFont)
    }

}