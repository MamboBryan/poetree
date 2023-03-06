apply(from = "$rootDir/base-android.gradle")

dependencies {

    implementation(project(Modules.common))
    implementation(project(Modules.androidUi))

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