[![publish](https://github.com/H4kt/ktor-vk-auth/actions/workflows/publish.yml/badge.svg?branch=master)](https://github.com/H4kt/ktor-vk-auth/actions/workflows/publish.yml)
![Maven latest version](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo.h4kt.dev%2Freleases%2Fdev%2Fh4kt%2Fxposed%2Fmaven-metadata.xml&logo=apachemaven)
![Kotlin](https://img.shields.io/badge/kotlin-1.9.22-7f52ff?logo=Kotlin&label=Kotlin)

# Xposed
Xposed provides additional utilities for [Exposed](https://github.com/JetBrains/Exposed)

## Installation
### Gradle
#### Kotlin
```kotlin
repositories {
  maven("https://repo.h4kt.dev/releases")
}

dependencies {
  implementation("dev.h4kt.xposed:xposed-<module>:<version>")
}
```

<details>

  <summary>Groovy</summary>

  You should really switch to Kotlin DSL, you know?

  Nevertheless

  ```groovy
  repositories {
    maven {
      url "https://repo.h4kt.dev/releases"
    }
  }

  dependencies {
    implementation "dev.h4kt.xposed:xposed-<module>:<version>"
  }
  ```
</details>

## Usage

### Core
Adds additional SQL operations like: `DISTINCT ON`, `TRUNC`, `EXTRACT`

A cleaner way to initialize transactions: `suspendedTransaction` and `suspendedTransactionIfNotInOne` which creates a transaction only if called outside of a transaction scope.

### Datetime
Adds an easy way of specifing the default value for `LocalDateTime` columns via `defaultNow(TimeZone)` and `defaultNowUTC`

### Codegen (Experimental)
Codegen module allows you to automatically generate a DTO, repository interface and implementation for your models/entities.

#### Install [KSP Gradle Plugin](https://github.com/google/ksp)
build.gradle.kts
```kotlin
plugins {
    kotlin("jvm") version "1.9.22"
    id("com.google.devtools.ksp") version "1.9.22-1.0.16"
}
```

#### Apply Xposed symbol processor
build.gradle.kts
```kotlin
dependencies {
    ksp("dev.h4kt.xposed:xposed-codegen:1.1.0")
    implementation("dev.h4kt.xposed:xposed-codegen:1.1.0")
}
```

#### Annotate your model
```kotlin
import dev.h4kt.xposed.codegen.annotations.Model

@Model
object Users : UUIDTable("user") {}
```

After completing all of the above steps Xposed will generate the following:
* DTO class for Users table `UserDto`
* Repository interface class `UserRepository`
* A basic implementation of it `XposedUserRepository`
