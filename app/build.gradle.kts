plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.safe.args)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
}

android {
    namespace = "com.techipinfotech.onlinestudy1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.techipinfotech.onlinestudy1"
        minSdk = 28
        targetSdk = 35
        versionCode = 11
        versionName = "3.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    Hilt
    implementation(libs.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
    ksp (libs.hilt.compiler)


//    Network
    implementation(libs.retrofit)
    implementation (libs.retrofit.converter.gson)
    implementation(libs.retrofit.logging.interceptor)

//  Coroutines
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.kotlinx.coroutines.core)

//  ViewModel LiveData Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.common.java8)


    // Other Library
    implementation (libs.glide)
    ksp (libs.glide.compiler)
    implementation (libs.dialogs.core)
    implementation (libs.dialogs.input)
    implementation (libs.lottie)

    implementation (libs.player.core)
    implementation("pub.devrel:easypermissions:3.0.0")
}