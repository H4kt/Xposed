package dev.h4kt.xposed.datetime.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.Column

fun Column<LocalDateTime>.defaultNow(
    timeZone: TimeZone
) = apply {
    defaultValueFun = {
        Clock.System.now().toLocalDateTime(timeZone)
    }
}

fun Column<LocalDateTime>.defaultNowUTC() = defaultNow(TimeZone.UTC)
