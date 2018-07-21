package com.unknown.numee.business.beans


data class User(
        val id: String = "",
        val email: String = "",
        val name: String = "",
        var child: Child? = null
)