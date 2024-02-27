package dev.h4kt.xposed.extensions

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.update

fun <T : Comparable<T>> IdTable<T>.findById(
    id: T
) = select { this@findById.id eq id }.firstOrNull()

fun <TId : Comparable<TId>, TTable : IdTable<TId>> TTable.updateById(
    id: TId,
    body: TTable.(UpdateStatement) -> Unit
) = update(
    where = { this@updateById.id eq id },
    body = body
)

fun <T : Comparable<T>> IdTable<T>.deleteById(
    id: T
) = deleteWhere { this@deleteById.id eq id }
