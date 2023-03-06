import org.gradle.api.artifacts.dsl.DependencyHandler

object ComposeDependencies {
    val icons = "org.jetbrains.compose.material:material-icons-extended-desktop:1.1.1"

}

object JetpackCompose {
    val ui by lazy { "androidx.compose.ui:ui:1.2.1" }
    val uiTooling by lazy { "androidx.compose.ui:ui-tooling:1.2.1" }
    val uiPreview by lazy { "androidx.compose.ui:ui-tooling-preview:1.2.1" }
    val foundation by lazy { "androidx.compose.foundation:foundation:1.2.1" }
    val material by lazy { "androidx.compose.material:material:1.2.1" }
    val activity by lazy { "androidx.activity:activity-compose:1.5.1" }
    val icons by lazy { "androidx.compose.material:material-icons-extended:1.2.1" }
    val navigation by lazy { "androidx.navigation:navigation-compose:2.5.3"}
}

object Accompanist{

    private const val VERSION = "0.26.3-beta"

    val pager by lazy { "com.google.accompanist:accompanist-pager:$VERSION"}
    val systemUi by lazy { "com.google.accompanist:accompanist-systemuicontroller:$VERSION"}
    val pagerIndicators by lazy { "com.google.accompanist:accompanist-pager-indicators:$VERSION"}
}

fun DependencyHandler.jetpackCompose() {
    implementation(JetpackCompose.ui)
    implementation(JetpackCompose.uiTooling)
    implementation(JetpackCompose.uiPreview)
    implementation(JetpackCompose.foundation)
    implementation(JetpackCompose.material)
    implementation(JetpackCompose.activity)
    implementation(JetpackCompose.icons)
    implementation(JetpackCompose.navigation)
}

fun DependencyHandler.accompanist(){
    implementation(Accompanist.systemUi)
    implementation(Accompanist.pager)
    implementation(Accompanist.pagerIndicators)
}
