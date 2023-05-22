package dev.h4kt.xposed.sql

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Function

class Trunc<T : Number>(
    val expr: Expression<*>
) : Function<T>(IntegerColumnType()) {

    override fun toQueryBuilder(
        queryBuilder: QueryBuilder
    ) = queryBuilder { append("TRUNC($expr)") }

}

fun <T : Number> ExpressionWithColumnType<out T>.trunc() = Trunc<T>(this)
