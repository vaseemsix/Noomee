package com.unknown.numee.business.beans

import java.util.*


data class ScheduleTemplates(
        var id: String = "",
        var name: String = "",
        var tasks: String = "",
        var date: Long = Date().time
)