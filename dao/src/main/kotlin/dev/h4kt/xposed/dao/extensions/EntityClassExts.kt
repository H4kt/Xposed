package dev.h4kt.xposed.dao.extensions

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder

fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.findOne(
    op: Op<Boolean>
): T? = find(op).firstOrNull()

fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.findOne(
    op: SqlExpressionBuilder.() -> Op<Boolean>
): T? = find(op).firstOrNull()

fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.exists(
    op: Op<Boolean>
): Boolean = count(op) > 0

fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.exists(
    op: SqlExpressionBuilder.() -> Op<Boolean>
): Boolean = count(SqlExpressionBuilder.op()) > 0

fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.notExists(
    op: Op<Boolean>
): Boolean = count(op) == 0L

fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.notExists(
    op: SqlExpressionBuilder.() -> Op<Boolean>
): Boolean = count(SqlExpressionBuilder.op()) == 0L
