buildscript {
    dependencies {
    }
}

plugins {

    id("com.android.application") version "7.3.0" apply false
    id("com.android.library") version "7.3.0" apply false
    id("org.jetbrains.compose") version "1.2.0" apply false
    id("io.realm.kotlin") version "1.2.0" apply false
    id("com.google.devtools.ksp") version "1.7.10-1.0.6" apply false
    id("com.rickclephas.kmp.nativecoroutines") version "0.13.0" apply false
    id("com.jetbrains.exposed.gradle.plugin") version "0.2.1" apply false
    id("org.flywaydb.flyway") version "5.2.4" apply false

    kotlin("android").version("1.7.10").apply(false)
    kotlin("multiplatform").version("1.7.10").apply(false)
    kotlin("plugin.serialization") .version("1.7.10").apply(false)

}
