![Kotlin version badge](https://img.shields.io/badge/kotlin-1.8.20-green)
![Maven central badge](https://maven-badges.herokuapp.com/maven-central/dev.h4kt.xposed/xposed-core/badge.svg?style=flat)

# Xposed
Xposed provides additional utilities for [Exposed](https://github.com/JetBrains/Exposed) which likely won't be added to it.

## How to use it
### Make sure you have Maven Central Repository enabled

build.gradle.kts
```kotlin
repositories {
  mavenCentral()
}
```
### Add your desired modules as dependencies

build.gradle.kts
```kotlin
dependencies {
  implementation("dev.h4kt.xposed:xposed-core:1.0")
  implementation("dev.h4kt.xposed:xposed-dao:1.0")
  implementation("dev.h4kt.xposed:xposed-kotlinx-datetime:1.0")
  implementation("dev.h4kt.xposed:xposed-serialization:1.0")
}
```

## Core
Adds additional SQL operations like: `DISTINCT ON`, `TRUNC`, `EXTRACT`

A cleaner way to initialize transactions: `suspendedTransaction` and `suspendedTransactionIfNotInOne` which creates a transaction only if called outside of a transaction scope.

## Dao
Adds some QOL methods to EntityClass like:
* `findOne` which, as the name implies, searches for only one entity (replaces `.first()` call every time you need one entity)
* `exists` and `notExists` which do not load the entity, but rather check if it exists (or not exists) in the database using `COUNT`

## Datetime
Adds an easy way of specifing the default value for `LocalDateTime` columns via `defaultNow(TimeZone)` and `defaultNowUTC`

## Serialization
Brings in the `Jsonb` type from `PostgeSQL` in combination with kotlinx serialization


## More to come
