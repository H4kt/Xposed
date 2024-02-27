package dev.h4kt.xposed.codegen.types

import kotlinx.serialization.Serializable

@Serializable
data class PageResult<T : Any>(
    val offset: Long,
    val limit: Int,
    val total: Long,
    val items: List<T>
)
