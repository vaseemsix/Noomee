package com.unknown.numee.business.beans

import java.util.*


data class ScheduleTemplates(
        val id: String = "",
        val name: String = "",
        val tasks: String = "",
        val date: Long = Date().time
)