# Xposed
A simple utility library for Exposed

Xposed provides additional utilities for [Exposed](https://github.com/JetBrains/Exposed) which likely won't be added to it.

## Core
Adds additional SQL operations like: `DISTINCT ON`, `TRUNC`, `EXTRACT`

A cleaner way to initialize transactions: `suspendedTransaction` and `suspendedTransactionIfNotInOne` which creates a transaction only if called outside of a transaction scope.

## Dao
Adds some QOL methods to EntityClass like:
* `findOne` which, as the name implies, searches for only one entity (replaces `.first()` call every time you need one entity)
* `exists` and `notExists` which do not load the entity, but rather check if it exists (or not exists) in the database using `COUNT`

## Datetime
Adds an easy way of specifing the default value for `LocalDateTime` columns.

## Serialization
Brings in the `Jsonb` type from `PostgeSQL` in combination with kotlinx serialization


## More to come
