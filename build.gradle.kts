import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {

    kotlin("jvm") version "1.8.0" apply false

    id("maven-publish")
    id("co.uzzu.dotenv.gradle") version "1.2.0"

}

subprojects {

    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "dev.h4kt.xposed"
    version = "1.0"

    afterEvaluate {
        archivesName.set("xposed-$name")
    }

    publishing {

        repositories {
            maven {

                name = "GitHubPackages"
                url = uri("https://github.com/H4kt/Xposed")

                credentials {
                    username = System.getenv("GITHUB_USERNAME") ?: env.fetchOrNull("GITHUB_USERNAME")
                    password = System.getenv("GITHUB_ACCESS_TOKEN") ?: env.fetchOrNull("GITHUB_ACCESS_TOKEN")
                }

            }
        }

        publications {
            register("Xposed${name.capitalized()}", MavenPublication::class) {
                artifactId = "xposed-${project.name}"
                from(components["kotlin"])
            }
        }

    }

}
