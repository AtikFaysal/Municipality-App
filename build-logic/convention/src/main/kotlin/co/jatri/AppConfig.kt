package co.jatri

import org.gradle.api.JavaVersion

object AppConfig {
    const val applicationId = "co.jatri.intercitynew"
    const val minimumSdkVersion = 21
    const val compileSdkVersion = 34
    const val targetSdkVersion = 34
    var testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    val compatibilityVersion = JavaVersion.VERSION_11
    const val versionCode  = 14
    const val versionName  = "1.1.3"
}