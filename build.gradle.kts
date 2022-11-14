buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    //alias(libs.plugins.versionCatalogCore)
    //alias(libs.plugins.versionCatalogUpdate)
}