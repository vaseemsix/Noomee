package com.unknown.numee.business.beans

import java.util.*


data class Schedule(
        val id: String = "",
        val name: String = "",
        val tasks: String = "",
        val date: Long = Date().time
)