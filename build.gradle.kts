import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {

    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22" apply false

    id("co.uzzu.dotenv.gradle") version "4.0.0"
    id("maven-publish")

}

repositories {
    mavenCentral()
}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    group = "dev.h4kt.xposed"
    version = "1.1.0"

    java {
        withSourcesJar()
        withJavadocJar()
    }

    repositories {
        mavenCentral()
    }

    kotlin {
        jvmToolchain(8)
    }

    afterEvaluate {
        archivesName.set("xposed-$name")
    }

    publishing {

        repositories {
            maven {

                name = "H4ktRepo"
                url = uri("https://repo.h4kt.dev/releases")

                authentication {
                    create<BasicAuthentication>("basic")
                }

                credentials {
                    username = env.H4KT_REPO_USERNAME.orNull()
                    password = env.H4KT_REPO_PASSWORD.orNull()
                }

            }
        }

        publications {
            register("Xposed${name.capitalized()}", MavenPublication::class) {
                artifactId = "xposed-${project.name}"
                from(components["java"])
            }
        }

    }

}
