plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("io.realm.kotlin")
}

version = "1.0"

kotlin {

    jvm()
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../ios/Podfile")
        framework {
            baseName = "common"
        }
    }

    sourceSets {

        // COMMON
        val commonMain by getting {
            dependencies {

                // kotlinx
                implementation(Kotlinx.dateTime)
                implementation(Kotlinx.coroutines)
                implementation(Kotlinx.serialization)

                // ktor
                implementation(KtorDependencies.core)
                implementation(KtorDependencies.json)
                implementation(KtorDependencies.logging)
                implementation(KtorDependencies.contentNegotiation)

                // other
                implementation(Multiplatform.napier)
                implementation(Multiplatform.realm)

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        // ANDROID
        val androidMain by getting
        val androidTest by getting

        // iOS
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

        // DESKTOP
        val jvmMain by getting
        val jvmTest by getting

    }
}

android {
    namespace = "com.mambo.poetree"
    compileSdk = 32
    defaultConfig {
        minSdk = 24
        targetSdk = 32
    }
}