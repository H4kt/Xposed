![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https://repo.h4kt.dev/releases/dev/h4kt/ktor-docs/maven-metadata.xml&logo=apachemaven&label=Maven&color=37bbbd)
![Kotlin](https://img.shields.io/badge/kotlin-1.9.22-7f52ff?logo=Kotlin&label=Kotlin)
[![Deploy](https://github.com/H4kt/Xposed/actions/workflows/deploy.yml/badge.svg)](https://github.com/H4kt/Xposed/actions/workflows/deploy.yml)

# Xposed
Xposed provides additional utilities for [Exposed](https://github.com/JetBrains/Exposed)

## How to use it
### Add repository

build.gradle.kts
```kotlin
repositories {
    maven("https://repo.h4kt.dev/releases")
}
```
### Add your desired modules as dependencies

build.gradle.kts
```kotlin
dependencies {
  implementation("dev.h4kt.xposed:xposed-core:1.1.0")
  implementation("dev.h4kt.xposed:xposed-codegen:1.1.0")
  implementation("dev.h4kt.xposed:xposed-kotlinx-datetime:1.1.0")
}
```

## Core
Adds additional SQL operations like: `DISTINCT ON`, `TRUNC`, `EXTRACT`

A cleaner way to initialize transactions: `suspendedTransaction` and `suspendedTransactionIfNotInOne` which creates a transaction only if called outside of a transaction scope.

## Datetime
Adds an easy way of specifing the default value for `LocalDateTime` columns via `defaultNow(TimeZone)` and `defaultNowUTC`

## Codegen (WIP)
Codegen module allows you to automatically generate a DTO, repository interface and implementation for your models/entities.

### How to use it?

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
