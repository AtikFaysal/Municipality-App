plugins {
    id("android.application")
    id("android.hilt")
    id("android.navigation")
}

android {
    namespace = "com.pourosova.data"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pourosova.data"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("gp-signature/esmart.jks")
            storePassword = "esmart"
            keyAlias = "esmart"
            keyPassword = "esmart"
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        release {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.dateced)

    implementation(libs.bundles.androidx.core.dependencies)
    implementation(libs.bundles.androidx.lifecycle.dependencies)
    implementation(libs.view.state.layout)
    implementation(libs.bundles.rxjava3.dependencies)
    implementation(libs.bundles.androidx.navigation.dependencies)
    debugImplementation(libs.leakcanary)
    implementation(libs.timber)

    implementation(libs.bundles.room.dependencies)
    implementation(libs.room.common)
    kapt(libs.room.compiler)

    implementation(libs.picasso)
    implementation(libs.cirlce.imageview)
    implementation(libs.date.picker)

    implementation(libs.bundles.network.dependencies)
    implementation(libs.bundles.rxjava3.dependencies)
    implementation(libs.kotlin.coroutines)

    implementation(libs.imagepicker)

    implementation(libs.androidx.constraint.layout)
    implementation(libs.custom.view)
    implementation(libs.androidx.appcompat)

    implementation(libs.bundles.android.responsive.size.dependencies)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.extjunit)
    androidTestImplementation(libs.test.espresso)
}