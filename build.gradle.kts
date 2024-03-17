plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.git)
}

val gitVersion: groovy.lang.Closure<String> by extra

android {
    namespace = "com.molikuner.android.notificationshade"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.molikuner.android.notificationshade"
        minSdk = 1
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-${gitVersion()}"
        }
        create("unsigned") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            applicationIdSuffix = ".unsigned"
            versionNameSuffix = "-${gitVersion()}"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
