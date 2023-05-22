package dev.h4kt.xposed.sql

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Function

class DistinctOn<T>(
    val expr: Expression<T>
) : Function<T>(IntegerColumnType()) {

    override fun toQueryBuilder(
        queryBuilder: QueryBuilder
    ) = queryBuilder {
        append("DISTINCT ON (", expr, ") ", expr)
    }

}

fun <T> ExpressionWithColumnType<T>.distinctOn() = DistinctOn(this)
