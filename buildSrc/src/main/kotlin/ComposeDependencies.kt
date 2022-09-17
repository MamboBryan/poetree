import org.gradle.api.artifacts.dsl.DependencyHandler

object ComposeDependencies {

}

object JetpackCompose {
    val ui by lazy { "androidx.compose.ui:ui:1.2.1" }
    val uiTooling by lazy { "androidx.compose.ui:ui-tooling:1.2.1" }
    val uiPreview by lazy { "androidx.compose.ui:ui-tooling-preview:1.2.1" }
    val foundation by lazy { "androidx.compose.foundation:foundation:1.2.1" }
    val material by lazy { "androidx.compose.material:material:1.2.1" }
    val activity by lazy { "androidx.activity:activity-compose:1.5.1" }

}

fun DependencyHandler.jetpackCompose() {
    implementation(JetpackCompose.ui)
    implementation(JetpackCompose.uiTooling)
    implementation(JetpackCompose.uiPreview)
    implementation(JetpackCompose.foundation)
    implementation(JetpackCompose.material)
    implementation(JetpackCompose.activity)
}

fun DependencyHandler.composeUi() {
//    implementation(compose.web.core)
//    implementation(compose.runtime)
}