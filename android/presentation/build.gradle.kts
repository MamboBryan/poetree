plugins {
    id("com.android.library")
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

dependencies {

    commonModule()
    features()

    implementation(project(":android:ui"))

    implementation(Libraries.core)
    implementation(Libraries.kotlin)
    implementation(Libraries.appCompat)
    implementation(Libraries.splashScreen)
    implementation(Libraries.materialDesign)
    implementation(Libraries.material3Design)
    implementation(Libraries.timber)
    
    jetpackCompose()

    accompanist()

}
