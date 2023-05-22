plugins {
    kotlin("jvm")
}

val exposedVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
}

kotlin {
    jvmToolchain(8)
}
