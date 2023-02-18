pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "Poetree"

// common module
include(":common")

// android
include(":android")
include(":android:app")
include(":android:presentation")

// ANDROID FEATURES
include(":android:features:getstarted")
include(":android:features:landing")
include(":android:features:authentication")
include(":android:features:account")


// desktop
include(":desktop")

// web
include(":web")

// backend
include(":backend")


