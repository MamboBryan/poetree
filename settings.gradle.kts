pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
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
include(":android:features:feed")


// desktop
include(":desktop")

// web
include(":web")

// backend
include(":backend")


include(":android:features:explore")
include(":android:features:bookmarks")
include(":android:features:library")
include(":android:features:compose")
include(":android:ui")
include(":android:features:settings")
include(":android:features:poem")
include(":android:features:comments")
include(":android:features:user")
