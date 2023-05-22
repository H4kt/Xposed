package dev.h4kt.xposed.sql

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Function
import java.math.BigDecimal
import java.time.LocalDateTime

enum class Part {
    YEAR,
    MONTH,
    DAY,
    HOUR,
    MINUTE,
    SECOND,
    EPOCH
}

class Extract(
    val expr: Expression<*>,
    val part: Part
) : Function<BigDecimal>(DecimalColumnType(10, 10)) {

    override fun toQueryBuilder(
        queryBuilder: QueryBuilder
    ) = queryBuilder {
        +"EXTRACT($part FROM "
        +expr
        +")"
    }

}

fun ExpressionWithColumnType<LocalDateTime>.extract(
    part: Part
) = Extract(
    expr = this,
    part = part
)
