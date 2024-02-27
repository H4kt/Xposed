package dev.h4kt.xposed.test

import dev.h4kt.xposed.annotations.ExperimentalApi
import dev.h4kt.xposed.codegen.annotations.Model
import dev.h4kt.xposed.codegen.generated.repositories.user.XposedUserRepository
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

enum class UserRole {
    DEFAULT, ADMIN
}

@OptIn(ExperimentalApi::class)
@Model
object Users : UUIDTable("user") {
    val name = varchar("name", 64)
    val role = enumerationByName<UserRole>("role", 32)
    val createdAt = datetime("created_at")
}

fun main() {
    XposedUserRepository().findAll()
}
