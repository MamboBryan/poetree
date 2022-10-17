plugins {
    id("com.android.library")
    id("com.google.devtools.ksp")
    kotlin("android")
}

android {

    compileSdk = AndroidSdk.compileSdk

    defaultConfig {
        minSdk = AndroidSdk.minSdk
        targetSdk = AndroidSdk.targetSdk
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

dependencies {

    implementation(project(Modules.common))

    implementation(Libraries.core)
    implementation(Libraries.kotlin)
    implementation(Libraries.appCompat)
    implementation(Libraries.splashScreen)
    implementation(Libraries.materialDesign)
    implementation(Accompanist.systemUi)

    implementation(Libraries.timber)
    
    jetpackCompose()

    // compose destinations
    ksp("io.github.raamcosta.compose-destinations:ksp:1.4.2-beta")
    implementation("io.github.raamcosta.compose-destinations:core:1.4.2-beta")

}
