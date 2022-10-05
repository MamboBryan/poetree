import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Modules {
    const val common = ":common"
    const val android = ":android"
    const val ios = ":ios"
    const val desktop = ":desktop"
}

fun DependencyHandler.commonModule(){
    implementation(project(Modules.common))
}