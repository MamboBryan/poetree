buildscript {
    dependencies {
    }
}

plugins {

    id("com.android.application").version("7.3.0").apply(false)
    id("com.android.library").version("7.3.0").apply(false)
    id("org.jetbrains.compose") version "1.1.1" apply false
    id("io.realm.kotlin") version "1.2.0" apply false
    id("com.google.devtools.ksp") version "1.6.21-1.0.5" apply false

    kotlin("android").version("1.6.21").apply(false)
    kotlin("multiplatform").version("1.6.21").apply(false)
    kotlin("plugin.serialization") .version("1.6.21").apply(false)

}
