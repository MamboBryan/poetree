import org.gradle.api.artifacts.dsl.DependencyHandler

object Firebase {
    val bom by lazy { "com.google.firebase:firebase-bom:29.0.4" }
    val analytics by lazy { "com.google.firebase:firebase-analytics-ktx" }
    val messaging by lazy { "com.google.firebase:firebase-messaging-ktx" }
    val storage by lazy { "com.google.firebase:firebase-storage-ktx" }
    val crashlytics by lazy { "com.google.firebase:firebase-crashlytics-ktx" }
    val performance by lazy { "com.google.firebase:firebase-perf-ktx" }
}

fun DependencyHandler.fireBaseStorage(){
    implementation(platform(Firebase.bom))
    implementation(Firebase.storage)
}

fun DependencyHandler.firebase() {
    implementation(platform(Firebase.bom))
    implementation(Firebase.storage)
    implementation(Firebase.analytics)
    implementation(Firebase.messaging)
    implementation(Firebase.performance)
    implementation(Firebase.crashlytics)
}