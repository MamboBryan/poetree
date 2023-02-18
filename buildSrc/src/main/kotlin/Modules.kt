import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Modules {
    const val common = ":common"
    const val android = ":android"
    const val ios = ":ios"
    const val desktop = ":desktop"
}

object Features {
    const val GET_STARTED = ":android:features:getstarted"
    const val LANDING = ":android:features:landing"
}

fun DependencyHandler.commonModule(){
    implementation(project(Modules.common))
}

fun DependencyHandler.features(){
    implementation(project(Features.GET_STARTED))
    implementation(project(Features.LANDING))
}