package dev.h4kt.xposed.codegen.extensions

internal fun String.singular(): String {
    return if (endsWith('s')) {
        dropLast(1)
    } else {
        this
    }
}
