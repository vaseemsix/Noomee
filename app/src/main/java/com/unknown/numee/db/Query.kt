package com.unknown.numee.db

sealed class OrderBy

data class Child(val name: String): OrderBy()
object Value : OrderBy()
object Key: OrderBy()

data class Query(
        val table: String = "",
        val orderBy: OrderBy = Key,
        val equalTo: String = ""
) {
}