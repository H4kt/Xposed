import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {

    kotlin("jvm") version "1.8.21" apply false

    id("signing")
    id("java-library")
    id("maven-publish")
    id("co.uzzu.dotenv.gradle") version "1.2.0"
    id("io.codearte.nexus-staging") version "0.30.0"

}

val ossrhUsername = System.getenv("OSSRH_USERNAME") ?: env.fetchOrNull("OSSRH_USERNAME")
val ossrhPassword = System.getenv("OSSRH_PASSWORD") ?: env.fetchOrNull("OSSRH_PASSWORD")

nexusStaging {

    packageGroup = "dev.h4kt.xposed"

    serverUrl = "https://s01.oss.sonatype.org/service/local/"
    username = ossrhUsername
    password = ossrhPassword

}

subprojects {

    apply(plugin = "signing")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "dev.h4kt.xposed"
    version = "1.0"

    java {
        withSourcesJar()
        withJavadocJar()
    }

    afterEvaluate {
        archivesName.set("xposed-$name")
    }

    publishing {

        repositories {

            maven {

                name = "MavenCentral"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

                credentials {
                    username = ossrhUsername
                    password = ossrhPassword
                }

            }

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
                from(components["java"])

                pom {

                    packaging = "jar"

                    name.set("Xposed")
                    url.set("https://github.com/H4kt/Xposed")
                    description.set("A simple utility library for Exposed")

                    scm {
                        connection.set("scm:https://github.com/H4kt/Xposed.git")
                        developerConnection.set("scm:git@github.com:H4kt/Xposed.git")
                        url.set("https://github.com/H4kt/Xposed")
                    }

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            id.set("H4kt")
                            name.set("H4kt")
                            email.set("h4ktoff@gmail.com")
                        }
                    }

                }

            }
        }

    }

    signing {
        publishing.publications.forEach(::sign)
    }

}
