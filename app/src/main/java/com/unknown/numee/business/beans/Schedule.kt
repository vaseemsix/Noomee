package com.unknown.numee.business.beans

data class ScheduleItem(
        val time: String = "",
        val taskID: String = "",
        var task: Task? = null
)

data class WeekDay(
        val status: String = ""
)


data class Schedule(
        val id: String = "",
        val name: String = "",
        val tasks: String = "",
        val weekDays: List<WeekDay> = listOf(),
        val items: List<ScheduleItem> = listOf()
)