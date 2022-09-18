import org.gradle.api.artifacts.dsl.DependencyHandler

object KtorDependencies {

    private const val VERSION = "2.1.1"

    val core by lazy { "io.ktor:ktor-client-core:$VERSION" }
    val cio by lazy { "io.ktor:ktor-client-cio:$VERSION" }
    val android by lazy { "io.ktor:ktor-client-okhttp:$VERSION" }
    val darwin by lazy { "io.ktor:ktor-client-darwin:$VERSION" }
    val js by lazy { "io.ktor:ktor-client-js:$VERSION" }
    val jvm by lazy { "io.ktor:ktor-client-jvm:$VERSION" }

    val logging by lazy { "io.ktor:ktor-client-logging:$VERSION" }

    val contentNegotiation by lazy { "io.ktor:ktor-client-content-negotiation:$VERSION" }
    val json by lazy { "io.ktor:ktor-serialization-kotlinx-json:$VERSION" }

}

fun DependencyHandler.ktorCore() {
    implementation(KtorDependencies.core)
    implementation(KtorDependencies.cio)
}

fun DependencyHandler.ktorAndroid() {
    implementation(KtorDependencies.android)
}

fun DependencyHandler.ktorDarwin() {
    implementation(KtorDependencies.darwin)
}

fun DependencyHandler.ktorJs() {
    implementation(KtorDependencies.js)
}

fun DependencyHandler.ktorJvm() {
    implementation(KtorDependencies.jvm)
}