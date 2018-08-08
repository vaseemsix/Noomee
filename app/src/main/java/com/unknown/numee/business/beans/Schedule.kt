package com.unknown.numee.business.beans

data class ScheduleItem(
        val time: String = "",
        val taskID: String = "",
        val task: Task? = null
)

data class Schedule(
        val id: String = "",
        val name: String = "",
        val items: List<ScheduleItem> = listOf()
)