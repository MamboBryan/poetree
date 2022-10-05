import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    commonModule()

    implementation(compose.desktop.currentOs)
    implementation(Multiplatform.napier)
    implementation(ComposeDependencies.icons)

}

compose.desktop {
    application {
        mainClass = "DesktopAppKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "poetree"
            packageVersion = "1.0.0"

            macOS {
                iconFile.set(project.file("icons/logo.icns"))
            }
            windows{
                iconFile.set(project.file("icons/logo.ico"))
            }
            linux {
                iconFile.set(project.file("icons/logo.png"))
            }

        }
    }
}
