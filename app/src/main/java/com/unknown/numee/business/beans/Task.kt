package com.unknown.numee.business.beans

enum class Status {
    TO_DO,
    CURRENT,
    DONE
}

data class Task(
        val id: String = "",
        val name: String = "",
        val description: String = "",
        val status: Status = Status.TO_DO,
        val numCount: Int = 1,
        val time: Int = 1, // in seconds
        val subTasks: List<SubTask> = listOf(),
        val userID: String = ""
)