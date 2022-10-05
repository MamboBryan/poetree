import org.gradle.api.artifacts.dsl.DependencyHandler

object Kotlinx {

    val serialization by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2" }
    val coroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4" }
    val dateTime by lazy { "org.jetbrains.kotlinx:kotlinx-datetime:0.3.2" }

}

fun DependencyHandler.kotlinxDependencies() {
    implementation(Kotlinx.serialization)
    implementation(Kotlinx.coroutines)
    implementation(Kotlinx.dateTime)
}