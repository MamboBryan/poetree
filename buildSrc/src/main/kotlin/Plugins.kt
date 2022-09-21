object Plugins {

    const val navigation = "androidx.navigation.safeargs.kotlin"
    const val application = "com.android.application"
    const val library = "com.android.library"
    const val hilt = "dagger.hilt.android.plugin"
    const val parcelize = "kotlin-parcelize"
    const val kotlinAndroid = "kotlin-android"
    const val kapt = "kotlin-kapt"
    const val serialize = "kotlinx-serialization"
    const val extensions = "kotlin-android-extensions"
    const val googleServices = "com.google.gms.google-services"
    const val crashlytics = "com.google.firebase.crashlytics"
    const val multiplatform = "multiplatform"

}

object BuildPlugins {
    val androidGradle by lazy { "com.android.tools.build:gradle:${Versions.androidGradlePlugin}" }
    val kotlinGradle by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinGradlePlugin}" }
    val googleServicesGradle by lazy { "com.google.gms:google-services:${Versions.googleServicesGradlePlugin}" }
    val hiltGradle by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}" }
    val navigationSafeArgsGradle by lazy { "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}" }
    val crashlyticsGradle by lazy { "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlyticsGradlePlugin}" }
    val kotlinxSerialization by lazy { "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}" }
}