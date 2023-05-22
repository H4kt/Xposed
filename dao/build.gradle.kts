plugins {
    kotlin("jvm")
}

val exposedVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
}

kotlin {
    jvmToolchain(8)
}
