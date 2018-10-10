package com.unknown.numee.business.beans

enum class Status {
    TO_DO,
    CURRENT,
    DONE
}

data class Task(
        var id: String = "",
        var name: String = "",
        val description: String = "",
        val status: Status = Status.TO_DO,
        val numCount: Int = 1,
        var time: String = "",
        var enable: Int = 1,
        var duration: Int = 1, // in seconds
        val subTasks: List<SubTask> = listOf()
)