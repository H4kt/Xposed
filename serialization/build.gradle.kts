val exposedVersion: String by project

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    implementation("org.postgresql:postgresql:42.5.1")

    implementation("dev.h4kt.xposed:xposed-core:1.0")

}

kotlin {
    jvmToolchain(8)
}
