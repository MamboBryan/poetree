import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Modules {
    const val common = ":common"
    const val android = ":android"
    const val androidUi = ":android:ui"
    const val ios = ":ios"
    const val desktop = ":desktop"
}

object Features {
    const val GET_STARTED = ":android:features:getstarted"
    const val LANDING = ":android:features:landing"
    const val AUTHENTICATION = ":android:features:authentication"
    const val ACCOUNT = ":android:features:account"
    const val FEED = ":android:features:feed"
    const val EXPLORE = ":android:features:explore"
    const val BOOKMARKS = ":android:features:bookmarks"
    const val LIBRARY = ":android:features:library"
    const val COMPOSE = ":android:features:compose"
}

fun DependencyHandler.commonModule(){
    implementation(project(Modules.common))
}

fun DependencyHandler.features(){
    implementation(project(Features.GET_STARTED))
    implementation(project(Features.LANDING))
    implementation(project(Features.AUTHENTICATION))
    implementation(project(Features.ACCOUNT))
    implementation(project(Features.FEED))
    implementation(project(Features.EXPLORE))
    implementation(project(Features.BOOKMARKS))
    implementation(project(Features.LIBRARY))
    implementation(project(Features.COMPOSE))
}