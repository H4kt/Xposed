package dev.h4kt.xposed.utils

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> suspendedTransaction(
    block: suspend Transaction.() -> T
): T = newSuspendedTransaction(Dispatchers.IO) {
    block()
}

suspend fun <T> suspendedTransactionIfNotInOne(
    block: suspend Transaction.() -> T
): T = try {
    block(TransactionManager.current())
} catch (ex: IllegalStateException) {
    suspendedTransaction(block)
}
