plugins {
    alias(libs.plugins.android)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
    alias(libs.plugins.ktlint)
}

val packageName = "cz.chm4.chmtestapp"

android {
    namespace = packageName
    compileSdk = 33

    defaultConfig {
        applicationId = packageName
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinComposeCompilerExtension.get()
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    // Because of KSP
    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }
}

dependencies {
    implementation(libs.bundles.androidX)
    implementation(libs.bundles.compose)
    // implementation(libs.timeTravel)
    implementation(libs.timber)

    implementation(libs.coilCompose)
    implementation(libs.retrofit)
    implementation(libs.retrofitMoshi)

    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    implementation(libs.hiltNavigationCompose)

    implementation(libs.bundles.room)
    ksp(libs.roomCompiler)

    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.androidTest)
    debugImplementation(libs.bundles.debug)
}

kapt {
    correctErrorTypes = true
}
