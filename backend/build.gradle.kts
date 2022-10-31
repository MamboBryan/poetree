val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm")
    id("com.jetbrains.exposed.gradle.plugin")
    id("org.flywaydb.flyway")
}

group = "com.mambo.poetree"
version = "0.0.5"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

exposedCodeGeneratorConfig {
    configFilename = "exposed.yml"
    user = System.getenv("JDBC_USER")
    password = System.getenv("JDBC_PASSWORD")
    databaseName = System.getenv("JDBC_DATABASE")
    databaseDriver = System.getenv("JDBC_DRIVER")
}

flyway {
    url = System.getenv("JDBC_DATABASE_URL")
    user = System.getenv("JDBC_USER")
    password = System.getenv("JDBC_PASSWORD")
    baselineOnMigrate = true
    locations = Array(1) { "filesystem:src/main/resources/database/migration" }
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {

    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // gson
    implementation("io.ktor:ktor-serialization-gson-jvm:$ktor_version")

    // jwt
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")

    // database
    implementation("org.jetbrains.exposed:exposed-core:0.38.2")
    implementation("org.jetbrains.exposed:exposed-dao:0.38.2")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.38.2")
    implementation("org.jetbrains.exposed:exposed-java-time:0.38.2")

    // postgres
    implementation("org.postgresql:postgresql:42.3.6")

    // hikari
    implementation("com.zaxxer:HikariCP:5.0.1")

    // flyaway
    implementation("org.flywaydb:flyway-core:9.0.4") {
        because("it is used for database migration")
    }

    // logging
    implementation("io.github.aakira:napier:2.6.1")

}

sourceSets.main {
    java.srcDirs("build/tables")
}

tasks.generateExposedCode {
    dependsOn("clean")
}

tasks {
    create("stage").dependsOn("installDist")
}
