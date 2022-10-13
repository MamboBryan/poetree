buildscript {
    dependencies {
//        classpath("io.realm.kotlin:gradle-plugin:1.0.2")
//        classpath("https://jitpack.io")
    }
}

plugins {

    id("com.android.application").version("7.3.0").apply(false)
    id("com.android.library").version("7.3.0").apply(false)
    id("org.jetbrains.compose") version "1.1.1" apply false
    id("io.realm.kotlin") version "1.2.0" apply false
    id("com.google.devtools.ksp") version "1.6.10-1.0.2" apply false

    kotlin("android").version("1.6.10").apply(false)
    kotlin("multiplatform").version("1.6.10").apply(false)
    kotlin("plugin.serialization").version("1.6.10").apply(false)
}
