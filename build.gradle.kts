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
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.create("release") {
                keyAlias = System.getenv("ANDROID_KEY_ALIAS")
                keyPassword = System.getenv("ANDROID_KEY_PASSWORD")
                storeFile = System.getenv("ANDROID_STORE_FILE")?.let(::file)
                storePassword = System.getenv("ANDROID_STORE_PASSWORD")

                enableV1Signing = true
                enableV2Signing = true
                enableV3Signing = true
                enableV4Signing = true
            }
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
