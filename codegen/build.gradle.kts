plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

val exposedVersion: String by project

dependencies {

    api(project(":core"))

    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.22-1.0.16")

    api("org.jetbrains.exposed:exposed-core:$exposedVersion")
    api("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.2")

}
