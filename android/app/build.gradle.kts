plugins {
    id("com.android.application")
    kotlin("android")
}

android {

    namespace = AndroidSdk.namespace
    compileSdk = AndroidSdk.compileSdk

    defaultConfig {
        applicationId = AndroidSdk.applicationId
        minSdk = AndroidSdk.minSdk
        targetSdk = AndroidSdk.targetSdk
        versionCode = AndroidSdk.versionCode
        versionName = AndroidSdk.versionName
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {

    implementation(project(":common"))
    implementation(project(":android:presentation"))

    implementation(Libraries.timber)
    implementation(Multiplatform.napier)

}